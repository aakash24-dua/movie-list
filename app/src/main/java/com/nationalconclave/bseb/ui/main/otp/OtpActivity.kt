package com.nationalconclave.bseb.ui.main.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.data.Constants
import com.nationalconclave.bseb.databinding.ActivityOtpBinding
import com.nationalconclave.bseb.ui.main.home.HomeActivity
import com.nationalconclave.bseb.utils.gone
import com.nationalconclave.bseb.utils.show
import com.nationalconclave.bseb.utils.showToast
import com.pixplicity.easyprefs.library.Prefs

class OtpActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(OtpViewModel::class.java)
    }

    private var countDownTimer : CountDownTimer?= null
    private var otpAttempts : Int= 0

    companion object{
        val USER_ID = "USER_ID"
        val PHONE_NO = "PHONE_NO"
        fun getIntent(context: Context,userId : String,phoneNo : String) : Intent{
            return Intent(context,OtpActivity::class.java).apply {
                putExtra(USER_ID,userId)
                putExtra(PHONE_NO,phoneNo)
            }

        }
    }

    lateinit var binding : ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userId = intent?.getStringExtra(USER_ID)?:""
        val phoneNo = intent?.getStringExtra(PHONE_NO)?:""
        title = "Enter Otp"
        viewModel.triggerOtp(OtpRequest(userId,phoneNo))
        val maskedPhoneNumber = phoneNo.replaceRange(3, 8, "*****")
        binding.otpMessage.text = binding.otpMessage.text.toString().plus(" $maskedPhoneNumber")
        binding.buttonSubmit.setOnClickListener {
            val code = binding.etOtp.text.toString()
            if (code.isBlank() || code.length != 6){
                showToast("Please enter 6 digit otp sent to your phone")
            }
            else {
                viewModel.validateOtp(OtpVerifyRequest(userId, phoneNo,code.toInt()))
            }
        }

        binding.tvResend.setOnClickListener {
            otpAttempts++
            viewModel.triggerOtp(OtpRequest(userId,phoneNo))
            isResendButtonEnable(false)
        }

        countDownTimer = object : CountDownTimer(30000, 1000) {
            // 60 seconds countdown with 1-second intervals
            override fun onTick(millisUntilFinished: Long) {
                // Update UI or perform any action on each tick
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvTimeleft.show()
                binding.tvTimeleft.text = "$secondsRemaining seconds"

                // Display the remaining time or update any UI element
                // Example: textView.setText("Seconds remaining: " + secondsRemaining);
            }

            override fun onFinish() {
                if (otpAttempts >=2 ){
                    isResendButtonEnable(false)
                    binding.tvResend.gone()
                    binding.tvTimeleft.gone()
                }
                else {
                    isResendButtonEnable(true)
                    binding.tvTimeleft.gone()
                }
            }
        }


        viewModel.publishObjectOtpResponse.subscribe { response->
            response.userId?.let { userId ->
                response?.phoneNo?.let { phoneNo ->
                    binding.tvResend.show()
                    countDownTimer?.start()
                    isResendButtonEnable(false)
                    showToast("Otp has been sent to your mobile number")
                }
            }

        }

        viewModel.publishObjectOtpVerifiedResponse.subscribe { response->
            response.token?.let { token ->
                //showToast("Otp has been verified")
                showToast("You are now logged in")
                Prefs.putString(Constants.token,token)
                Prefs.putBoolean(Constants.isLoggedIn,true)
                val intent = Intent(this@OtpActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)

            }

        }

        viewModel.showToast.subscribe{ errorString->
            Toast.makeText(this,errorString, Toast.LENGTH_LONG).show()

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            // Handle the back button press event
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    private fun isResendButtonEnable(enabled: Boolean) {
        binding.tvResend.isEnabled = enabled
        binding.tvResend.isClickable = enabled
        binding.tvResend.setTextColor(
             resources.getColor(if(enabled) R.color.red else R.color.grey_700)
        )

    }
}
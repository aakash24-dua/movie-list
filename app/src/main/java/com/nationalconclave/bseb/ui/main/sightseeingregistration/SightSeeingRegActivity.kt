package com.nationalconclave.bseb.ui.main.sightseeingregistration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nationalconclave.bseb.data.Constants
import com.nationalconclave.bseb.databinding.ActivitySightSeeingBinding
import com.nationalconclave.bseb.ui.main.registration.RegistrationRequest
import com.nationalconclave.bseb.utils.getParcelable
import com.nationalconclave.bseb.utils.putParcelable
import com.nationalconclave.bseb.utils.showToast
import com.pixplicity.easyprefs.library.Prefs

class SightSeeingRegActivity : AppCompatActivity() {

    lateinit var binding : ActivitySightSeeingBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SightSeeingViewModel::class.java)
    }

    private val list = arrayListOf<String>()

    companion object {
        private val REGISTRATION_REQUEST = "REGISTRATION_REQUEST"
        fun getIntent(context: Context, registrationRequest: RegistrationRequest? = null) : Intent {
            return Intent(context, SightSeeingRegActivity::class.java).apply {
                registrationRequest?.let {
                    putParcelable(REGISTRATION_REQUEST,it)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySightSeeingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Sight Seeing Interests")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = listOf("Nalanda and Rajgir", "Bodhgaya", "Vaishali", "Patna") // Example items

        val adapter = CheckboxAdapter(this, items)
        //binding.listView.adapter = adapter

        intent.getParcelable<RegistrationRequest>(REGISTRATION_REQUEST)

        binding.buttonRegister.setOnClickListener {
//            intent.getParcelable<RegistrationRequest>(REGISTRATION_REQUEST)
//                ?.let { registrationRequest ->
            list.clear()
            if (binding.radio1.isChecked) {
                list.add(binding.radio1.text.toString())
            }

            if (binding.radio2.isChecked) {
                list.add(binding.radio2.text.toString())
            }

            if (binding.radio3.isChecked) {
                list.add(binding.radio3.text.toString())
            }

            if (binding.radio4.isChecked) {
                list.add(binding.radio4.text.toString())
            }
            val listt = list
            var isSightSelected = false
            if (listt.isNotEmpty()) {
                isSightSelected = true
            }
            val request = RegistrationRequest(isSightSelected = isSightSelected, siteSeeing = listt)
            //registrationRequest.siteSeeing = listt
            val token = Prefs.getString(Constants.token, "")
            viewModel.updateUser(request,token)

            // }

        }

//        binding.buttonSkipAndRegister.setOnClickListener {
//            intent.getParcelable<RegistrationRequest>(REGISTRATION_REQUEST)
//                ?.let { registrationRequest ->
//                    viewModel.registerUser(registrationRequest)
//                }
//        }


        viewModel.publishObject.subscribe { response->
            response.id?.let { userId ->
                response?.phoneNo?.let { phoneNo ->
                    showToast("Details Updated")
                    finish()
                   // startActivity(OtpActivity.getIntent(this@SightSeeingRegActivity,userId,phoneNo))
                }
            }
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

}
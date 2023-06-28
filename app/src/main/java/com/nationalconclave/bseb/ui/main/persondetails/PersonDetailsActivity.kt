package com.nationalconclave.bseb.ui.main.persondetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nationalconclave.bseb.databinding.ActivityPersonDetailsBinding
import com.nationalconclave.bseb.utils.gone
import com.nationalconclave.bseb.utils.show


class PersonDetailsActivity : AppCompatActivity() {

    companion object{
        val TITLE = "title"
        val NAME = "name"
        val MOBILE = "mobile"
        val CARNO = "car_no"
        fun getIntent(context: Context,title:String,name:String,mobile:String,carNo : String? = null) : Intent{
            return Intent(context,PersonDetailsActivity::class.java).apply {
                putExtra(TITLE,title)
                putExtra(NAME,name)
                putExtra(MOBILE,mobile)
                carNo?.let {
                    putExtra(CARNO,carNo)
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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    lateinit var binding : ActivityPersonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(intent.getStringExtra(TITLE)?:"")
        val phone = intent.getStringExtra(MOBILE)?:""
        binding.name.text = intent.getStringExtra(NAME)?:""
        binding.number.text = intent.getStringExtra(MOBILE)?:""
        binding.carNumber.gone()
        intent.getStringExtra(CARNO)?.let {
            binding.carNumber.show()
            binding.carNumber.text = it
        }
        binding.tvCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + phone)
            startActivity(callIntent)
        }

    }
}
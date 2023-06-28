package com.nationalconclave.bseb.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.data.Constants
import com.nationalconclave.bseb.databinding.ActivityHomeBinding
import com.nationalconclave.bseb.ui.main.persondetails.PersonDetailsActivity
import com.nationalconclave.bseb.ui.main.registration.RegistrationActivity
import com.nationalconclave.bseb.ui.main.sightseeingregistration.SightSeeingRegActivity
import com.nationalconclave.bseb.ui.main.webview.WebViewActivity
import com.nationalconclave.bseb.utils.setVisible
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding
    private var isLoggedIn : Boolean = false
    private var userDataReceived : Boolean = false
    private var userResponse : UserResponse? = null
    private var myMenu: Menu? = null
    private var isSightSelected: Boolean = false

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("National Conclave")
        isLoggedIn = Prefs.getBoolean(Constants.isLoggedIn,false)

        //isLoggedIn = true
        binding.tvRegister.setVisible(isLoggedIn.not())


        viewModel.publishObjectResponse.subscribe { response->
            userResponse = response
            userDataReceived = true
            isSightSelected = response.isSightSelected
            invalidateOptionsMenu()
        }



        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        val day1Url = Constants.baseUrl.plus("/static-content/1")
        val day2Url = Constants.baseUrl.plus("/static-content/2")

        binding.clDay1.setOnClickListener {
            startActivity(WebViewActivity.getIntent(this@HomeActivity,day1Url,getString(R.string.conclave_day1)))
        }

        binding.clDay2.setOnClickListener {
            startActivity(WebViewActivity.getIntent(this@HomeActivity,day2Url, getString(R.string.conclave_day2)))
        }

//        binding.clDay3.setOnClickListener {
//            startActivity(WebViewActivity.getIntent(this@HomeActivity,day3Url,getString(R.string.conclave_day3_with_optional)))
//        }



        val item1 = BannerData(R.drawable.mahabodhi,"Mahabodhi Temple (Bodh)",getString(R.string.mahabodh),"https://tourism.bihar.gov.in/en/destinations/gaya")
        val item2 =  BannerData(R.drawable.patna_museum,"Bihar Museum (Patna)",getString(R.string.patna_museum),"https://tourism.bihar.gov.in/en/destinations/patna")
        val item3 = BannerData(R.drawable.ruins_of_nalanda,"Ruins Of Nalanda (Nalanda)",getString(R.string.mahabodh),"https://tourism.bihar.gov.in/en/destinations/nalanda/nalanda-ka-khandahar")
        val item4 = BannerData(R.drawable.vaishali,"Vaishali",getString(R.string.vaishali),"https://tourism.bihar.gov.in/en/destinations/vaishali")

        val list = arrayListOf(item1, item2, item3,item4)
        val adapter = CustomAdapter(activity = this, list = list)
        binding.viewPager.adapter = adapter

        startAutoScroll()



    }

    override fun onResume() {
        super.onResume()
        val token = Prefs.getString(Constants.token,"")
        if (isLoggedIn){
            viewModel.getUserData(token)
        }
    }

    private fun startAutoScroll() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = viewPager.currentItem
                val totalItems = viewPager.adapter?.count ?: 0
                val nextItem = (currentItem + 1) % totalItems
                viewPager.setCurrentItem(nextItem, true)
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 3000, 3000) // Auto-scroll every 3 seconds (adjust as needed)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    // Resume auto-scroll when user interaction stops
                    handler.postDelayed(runnable, 3000)
                } else {
                    // Pause auto-scroll when user interacts with the ViewPager
                    handler.removeCallbacks(runnable)
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (isLoggedIn && userDataReceived) {
            menuInflater.inflate(R.menu.menu_main, menu)
            val sightSeeingLink = menu?.findItem(R.id.menu_item3)
            val sightSeeingCities = menu?.findItem(R.id.menu_item4)
            sightSeeingLink?.isVisible = isSightSelected
            sightSeeingCities?.isVisible = isSightSelected.not()
            true
        } else false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when(id){
            R.id.menu_item1 -> {
                userResponse?.assignedDriver?.let {
                    startActivity(
                        PersonDetailsActivity.getIntent(
                            this,
                            getString(R.string.driver_details),
                            it?.name ?: "Ram",
                            it?.phoneNo ?: "8800454637",
                            carNo = it?.carNo ?: "HR 26 DQ 5551"
                        )
                    )
                }
                return true
            }
            R.id.menu_item2 -> {
                userResponse?.assignedOfficer?.let {
                    startActivity(
                        PersonDetailsActivity.getIntent(
                            this,
                            getString(R.string.liaison_officer_details),
                            it?.name?:"Arjun",
                            it?.phoneNo?:"8800452451"
                        )
                    )
                }
                return true
            }
            R.id.menu_item3 -> {
                val day3Url = Constants.baseUrl.plus("/static-content/3")
                startActivity(WebViewActivity.getIntent(this@HomeActivity,day3Url,getString(R.string.conclave_day3_with_optional)))
                return true
            }
            R.id.menu_item4 -> {
                startActivity(SightSeeingRegActivity.getIntent(context = this@HomeActivity))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
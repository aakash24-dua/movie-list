package com.nationalconclave.bseb.ui.main.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.nationalconclave.bseb.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityWebviewBinding
    companion object{
        val URL = "url"
        val DAY = "day"

        fun getIntent(context: Context,url:String,day : String) : Intent{
            return Intent(context,WebViewActivity::class.java).apply {
                putExtra(URL,url)
                putExtra(DAY,day)
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(intent.getStringExtra(DAY)?:"")
        val url = intent.getStringExtra(URL)?:""
        binding.webView.loadUrl(url)
        binding.webView.getSettings().setAppCacheEnabled(false)
        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)

    }
}
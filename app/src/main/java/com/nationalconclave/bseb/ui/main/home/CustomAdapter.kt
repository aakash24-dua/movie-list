package com.nationalconclave.bseb.ui.main.home

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.ui.main.webview.WebViewActivity


class CustomAdapter(
    private val activity: Activity,
    private val list: ArrayList<BannerData>
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = activity.layoutInflater
        val viewItem: View = inflater.inflate(R.layout.layout_banner, container, false)
        val imageView: ImageView = viewItem.findViewById(R.id.image_view) as ImageView
        imageView.setImageResource(list[position].bannerImage)
        val textView1 = viewItem.findViewById(R.id.title_text_view) as TextView
        val placeDetailsText = viewItem.findViewById(R.id.place_details_text) as TextView
        textView1.text = list[position].bannerTitle
        placeDetailsText.text = list[position].bannerSubtitle
        viewItem.setOnClickListener {
            container.context.startActivity(WebViewActivity.getIntent(container.context,list[position].bannerLink,list[position].bannerTitle))
        }
        (container as ViewPager).addView(viewItem)
        return viewItem
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun getCount(): Int {

        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}

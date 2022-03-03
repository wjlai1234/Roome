package com.example.roome.onboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.roome.R
import com.squareup.picasso.Picasso


class SlideViewPagerAdapter(private val ctx: Context) : PagerAdapter() {


    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater: LayoutInflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slide_screen, null)

        var logo: ImageView = view.findViewById(R.id.logo)

        var ind1: ImageView = view.findViewById(R.id.dot1)
        var ind2: ImageView = view.findViewById(R.id.dot2)
        var ind3: ImageView = view.findViewById(R.id.dot3)

        var title: TextView = view.findViewById(R.id.title)
        var desc: TextView = view.findViewById(R.id.desc)

        when (position) {
            0 -> {
                Picasso.get().load(R.drawable.onboarding1).into(logo)
                ind1.setImageResource(R.drawable.dot_selected)
                ind2.setImageResource(R.drawable.dot_unselected)
                ind3.setImageResource(R.drawable.dot_unselected)

                title.text = "Plan your trips"
                desc.text = "Book one of our unique hotel to escape the ordinary"
            }
            1 -> {
                Picasso.get().load(R.drawable.onboarding2).into(logo)
                ind1.setImageResource(R.drawable.dot_unselected)
                ind2.setImageResource(R.drawable.dot_selected)
                ind3.setImageResource(R.drawable.dot_unselected)

                title.text = "Find best deals"
                desc.text = "Find deals for any season from cosy country homes to city flats"
            }
            2 -> {
                Picasso.get().load(R.drawable.onboarding3).into(logo)
                ind1.setImageResource(R.drawable.dot_unselected)
                ind2.setImageResource(R.drawable.dot_unselected)
                ind3.setImageResource(R.drawable.dot_selected)

                title.text = "Get Most Option"
                desc.text = "Roome have provide a lot of good hotel services than others"
            }
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
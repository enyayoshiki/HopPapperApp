package com.example.hotpapperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.hotpapperapp.databinding.ItemTablayoutBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class MainViewPagerAdapter (fm: FragmentManager, private val context: Context)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var isEnable = true


    fun setPagingEnabled(isEnable: Boolean) {
        this.isEnable = isEnable
    }



    //タイトルの配列
    private val tabItem = arrayOf(
        listOf(R.string.title_home, R.drawable.icon_home_false),
        listOf(R.string.title_near, R.drawable.icon_research_black),
        listOf(R.string.title_favorite, R.drawable.icon_favorite)
    )

    //Fragmentの配列
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment.newInstance()
            1 -> CandidateResearchFragment.newInstance()
            else -> MapsFragment.newInstance()
        }
    }

    // タブの名前を出力
    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(tabItem[position][0])
    }

    // タブの個数
    override fun getCount(): Int {
        return tabItem.size
    }

    fun getTabView(tabLayout: TabLayout, position: Int): View?  {

        val binding = DataBindingUtil.inflate<ItemTablayoutBinding>(
            LayoutInflater.from(context),
            R.layout.item_tablayout,
            tabLayout,
            false
        )

        //タイトルの編集
        binding?.apply {
            titleItemTabLayout?.text =  context.getText(tabItem[position][0])
            iconItemTabLayout.setImageResource(tabItem[position][1])
        }

//        Picasso.get().load(tabItem[position][1] ) .into(binding.iconItemTabLayout)
        return binding?.root
    }

    fun addCandidateFragmetn() : Fragment{
        return CandidateResearchFragment.newInstance()
    }

//    private fun getTabItem(num: Int): List<Int> {
//        return when(num){
//            0 -> listOf(R.string.title_home, R.drawable.icon_home_false)
//            1 -> listOf(R.string.title_near, R.drawable.icon_research_black)
//            2 -> listOf(R.string.title_favorite, R.drawable.icon_favorite)
//            else -> listOf()
//        }
//    }
}
package com.example.hotpapperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.hotpapperapp.databinding.ActivityMainBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: ResearchViewModel by viewModels {
        ResearchViewModelFactory(
            lifecycleScope
        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        //View(binding)とViewModelの設定
        setBindig()

        //ViewPagerの設定
        setViewPagerAdapter()



    }

    private fun setBindig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewmodel = viewModel
                cancelKeywordButton.setOnClickListener {

            }
        }
//        setViewModel()

    }

    private fun setViewModel(){
        Timber.d("Timber setViewModel")

        viewModel.isCreateReserchFragment.observe( this, Observer {
            Timber.d("Timber observe")
            val fragment = CandidateResearchFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.viewPager_main, fragment)
            fragmentTransaction.commit()

        })
    }

    private fun chaneViewPagerSwipe(isEnable: Boolean) {
        val viewPager2 = ViewPager2(this)
        viewPager2.isUserInputEnabled = isEnable
    }

    //ViewPagerの設定
    private fun setViewPagerAdapter() {
        //SupportMapFragmentの準備
        val adapter = MainViewPagerAdapter(
            supportFragmentManager,
            this
        )
        binding.viewPagerMain.adapter = adapter
        //TabLayoutの設定
        setTabLayout(adapter)
    }

    //TabLayoutの設定
    private fun setTabLayout(adapter: MainViewPagerAdapter){
        binding.tabLayout.setupWithViewPager(binding.viewPagerMain)
        for (i in 0 until adapter.count) {
            val tab: TabLayout.Tab? = binding.tabLayout.getTabAt(i)
            tab?.customView = adapter.getTabView(binding.tabLayout, i)
        }
        setCandidateFragment()
        //ViewPagerAdapterを制限できる？？
//        adapter.setPagingEnabled(false)

    }

    //CandidateFragmentの生成
    private fun setCandidateFragment() {

//        val firstFragment = CandidateResearchFragment()
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.add(R.id.viewPager_container, firstFragment)
//        fragmentTransaction.commit()
    }


}

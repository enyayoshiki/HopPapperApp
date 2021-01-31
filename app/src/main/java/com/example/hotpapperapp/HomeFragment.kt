package com.example.hotpapperapp

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotpapperapp.databinding.ChildFragmentHomeMainBinding

class HomeFragment : Fragment(R.layout.child_fragment_home_main)  {

//    private val viewModel: TestEditCycleViewModel by activityViewModels()

    private lateinit var binding: ChildFragmentHomeMainBinding
    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory(
            lifecycleScope
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

    }



    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
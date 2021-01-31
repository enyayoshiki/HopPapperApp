package com.example.hotpapperapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hotpapperapp.databinding.ChildFragmentCandidateResearchBinding

class CandidateResearchFragment: Fragment(R.layout.child_fragment_candidate_research) {

    lateinit var binding: ChildFragmentCandidateResearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment_candidate_research, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return
    }


    companion object {
        fun newInstance(): CandidateResearchFragment {
            return CandidateResearchFragment()
        }
    }
}
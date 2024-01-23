package com.own_world.instragramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.own_world.instragramclone.Adapters.ReelAdpter
import com.own_world.instragramclone.R
import com.own_world.instragramclone.databinding.FragmentReelsBinding

class ReelsFragment : Fragment() {
    private lateinit var binding : FragmentReelsBinding
    lateinit var adapter : ReelAdpter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentReelsBinding.inflate(inflater, container, false)
        return binding.root
    }

}
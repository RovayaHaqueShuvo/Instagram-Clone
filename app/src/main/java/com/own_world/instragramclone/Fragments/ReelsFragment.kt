package com.own_world.instragramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.Adapters.ReelAdpter
import com.own_world.instragramclone.Modals.Reel
import com.own_world.instragramclone.R
import com.own_world.instragramclone.Util.REEL
import com.own_world.instragramclone.databinding.FragmentReelsBinding

class ReelsFragment : Fragment() {
    private lateinit var binding : FragmentReelsBinding
    lateinit var adapter : ReelAdpter
    var reelList = ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentReelsBinding.inflate(inflater, container, false)
        adapter= ReelAdpter(requireContext(),reelList)
        binding.viewpaper.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            reelList.clear()

            for(i in it.documents){
                var reel = i.toObject<Reel>()
                tempList.add(reel!!)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

}
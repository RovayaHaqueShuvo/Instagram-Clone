package com.own_world.instragramclone.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.Adapters.ViewPagerAdapter
import com.own_world.instragramclone.Modals.User
import com.own_world.instragramclone.SingUpActivity
import com.own_world.instragramclone.Util.USER_NODE
import com.own_world.instragramclone.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewpagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)



        binding.editProfileBtn.setOnClickListener {
            val intent = Intent(activity, SingUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        viewpagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewpagerAdapter.addFragment(MyPostFragment(),"MY POST")
        viewpagerAdapter.addFragment(MyReelsFragment(),"MY REEL")

        binding.viewPager.adapter = viewpagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root

    }


    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {

                val user: User = it.toObject<User>()!!
                binding.nameProfile.text = user.userName
                binding.bio.text = user.email

                if (!user.image.isNullOrEmpty()) {
                    Picasso.get().load(user.image).into(binding.profileImage)
                }

            }
    }
}
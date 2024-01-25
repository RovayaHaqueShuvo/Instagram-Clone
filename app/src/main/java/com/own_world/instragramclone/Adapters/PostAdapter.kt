package com.own_world.instragramclone.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.Modals.Post
import com.own_world.instragramclone.Modals.User
import com.own_world.instragramclone.R
import com.own_world.instragramclone.Util.USER_NODE
import com.own_world.instragramclone.databinding.PostRvBinding

class PostAdapter(var context: Context, var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root){
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
      return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).name).get().addOnSuccessListener {
            var user  = it.toObject<User>()!!
            Glide.with(context).load(user.image).placeholder(R.drawable.account_circle).into(holder.binding.profileImage)
            holder.binding.nameProfile.text =user.userName
        }

        }catch (e:Exception){}

        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.add_circle).into(holder.binding.imageView6)

        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())

            holder.binding.time.text= text
        }catch (e:Exception){

        }

        holder.binding.share.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(Intent.createChooser(i,"Share"))

        }

        holder.binding.caption.text =postList.get(position).caption
        holder.binding.like.setOnClickListener {
                holder.binding.like.setImageResource(R.drawable.likered)


        }
    }
}
package com.own_world.instragramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.own_world.instragramclone.Modals.Reel
import com.own_world.instragramclone.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelAdpter(var context: Context, var reelList : ArrayList<Reel>): RecyclerView.Adapter<ReelAdpter.ViewHolder>(){

    inner class ViewHolder(var binding : ReelDgBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDgBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).into(holder.binding.profileImage)
        holder.binding.caption.setText(reelList.get(position).caption)

        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener {
            it.start()

        }
    }
}
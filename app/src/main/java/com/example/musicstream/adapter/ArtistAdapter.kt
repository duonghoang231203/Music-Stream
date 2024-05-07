package com.example.musicstream.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstream.SongsListActivity
import com.example.musicstream.databinding.ArtistItemRecyclerRowBinding
import com.example.musicstream.models.ArtistModel

class ArtistAdapter (private val artistList: List<ArtistModel>) :
    RecyclerView.Adapter<ArtistAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding : ArtistItemRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root){
        //bind the data with views
        fun bindData(artist : ArtistModel){
            binding.nameTextView.text = artist.name
            Glide.with(binding.coverImageView).load(artist.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)

            //Start SongsList Activity
            val context = binding.root.context
            binding.root.setOnClickListener {
                SongsListActivity.artist = artist
                context.startActivity(Intent(context, SongsListActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ArtistItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(artistList[position])
    }
}
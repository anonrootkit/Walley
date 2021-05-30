package com.example.walley.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.walley.databinding.PhotoListItemBinding
import com.example.walley.model.Photo
import com.squareup.picasso.Picasso


class PhotosAdapter(private val layoutInflater : LayoutInflater) : ListAdapter<Photo, PhotosAdapter.ViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.photoId == newItem.photoId
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(val binding : PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhotoListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.photographerName.text = photo.photographer
//        Picasso.get().load(photo.photoUrl).into(holder.binding.photo)

        val url = GlideUrl(
            photo.photoUrl.url, LazyHeaders.Builder()
                .addHeader("Authorization", "563492ad6f91700001000001e6b41b62369e497a8cf98acfc5521947")
                .build()
        )
        Glide.with(holder.binding.photo).load(url).into(holder.binding.photo)

//        holder.binding.executePendingBindings()
    }
}
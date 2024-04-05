package com.photosofmars.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.photosofmars.R
import com.photosofmars.databinding.FragmentItemListPhotoBinding
import com.photosofmars.entity.Photo


class ListPhotoAdapter(): RecyclerView.Adapter<ListPhotoHolder>() {
    private var photos: List<Photo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setPhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPhotoHolder {
        return ListPhotoHolder(
            FragmentItemListPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListPhotoHolder, position: Int) {
        val photo = photos.getOrNull(position)
        with(holder.binding) {
            photo?.let {
                Glide
                    .with(photoImageview.context)
                    .load(it.imgSrc)
                    .centerCrop()
                    .into(photoImageview)
            }

            roverTextView.text = "${holder.itemView.context.getString(R.string.rover)}: ${photo?.rover?.name}"
            solTextView.text = "${holder.itemView.context.getString(R.string.sol)}: ${photo?.sol}"
            cameraTextView.text = "${holder.itemView.context.getString(R.string.camera)}: ${photo?.camera?.name}"
            dateTextView.text = "${holder.itemView.context.getString(R.string.date)}: ${photo?.date}"
        }
    }

    override fun getItemCount() = photos.size
}

class ListPhotoHolder (val binding: FragmentItemListPhotoBinding): RecyclerView.ViewHolder(binding.root)
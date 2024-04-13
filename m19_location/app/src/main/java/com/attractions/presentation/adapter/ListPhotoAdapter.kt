package com.attractions.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.attractions.databinding.FragmentItemListPhotoBinding
import com.attractions.entity.Photo
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale


private const val FORMAT = "yyyy-MM-dd"

class ListPhotoAdapter(
    private val onItemClicked: (Photo) -> Unit
): ListAdapter<Photo, ListPhotoAdapter.ListPhotoHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPhotoHolder {
        return ListPhotoHolder(
            FragmentItemListPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListPhotoHolder, position: Int) {
        val photo = getItem(position)
        with(holder.binding) {
            Glide
                .with(photoImageview.context)
                .load(photo.uri)
                .centerCrop()
                .into(photoImageview)

            dateTextView.text = SimpleDateFormat(FORMAT, Locale.getDefault()).format(photo.date.time)

            photoCardView.setOnClickListener {
                onItemClicked(photo)
            }
        }
    }

    inner class ListPhotoHolder (val binding: FragmentItemListPhotoBinding): RecyclerView.ViewHolder(binding.root)

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}


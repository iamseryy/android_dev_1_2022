package com.photosofmars.data.dto

import com.google.gson.annotations.SerializedName
import com.photosofmars.entity.Camera
import com.photosofmars.entity.Photo
import com.photosofmars.entity.Rover

class PhotoDto(
    @SerializedName("id") override val id: Int,
    @SerializedName("sol") override val sol: String,
    @SerializedName("camera") override val camera: Camera,
    @SerializedName("rover") override  val rover: Rover,
    @SerializedName("date") override val date: String,
    @SerializedName("imgSrc") override val imgSrc: String
): Photo
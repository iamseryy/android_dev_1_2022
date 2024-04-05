package com.photosofmars.data.dto


import com.google.gson.annotations.SerializedName
import com.photosofmars.entity.Photo


data class PhotoDto(
    @SerializedName("id") override val id: Int,
    @SerializedName("sol") override val sol: String,
    @SerializedName("camera") override val camera: CameraDto,
    @SerializedName("rover") override  val rover: RoverDto,
    @SerializedName("earth_date") override val date: String,
    @SerializedName("img_src") override val imgSrc: String
): Photo


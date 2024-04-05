package com.photosofmars.data.dto

import com.photosofmars.entity.Camera

data class CameraDto (
    override val id: Int,
    override val name: String
): Camera
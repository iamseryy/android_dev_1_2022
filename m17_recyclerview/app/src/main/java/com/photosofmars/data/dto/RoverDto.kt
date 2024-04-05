package com.photosofmars.data.dto

import com.photosofmars.entity.Rover

data class RoverDto(
    override val id: Int,
    override val name: String
): Rover
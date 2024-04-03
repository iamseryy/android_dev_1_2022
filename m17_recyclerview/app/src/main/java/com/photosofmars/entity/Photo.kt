package com.photosofmars.entity

interface Photo {
    val id: Int
    val sol: String
    val camera: Camera
    val rover: Rover
    val date: String
    val imgSrc: String
}
package com.attractions.entity

interface Feature {
    val type: String
    val id: String
    val geometry: Point
    val properties: Property
}
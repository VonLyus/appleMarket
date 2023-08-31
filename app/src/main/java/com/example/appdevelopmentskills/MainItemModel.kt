package com.example.appdevelopmentskills

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainItemModel(
    val img: Int,
    val title: String,
    val location: String,
    val description: String,
    val name: String,
    val price: String,
    val numOfChat: Int,
    var numOfThumbs: Int,
    var thumbsCheck: Int
    ) : Parcelable

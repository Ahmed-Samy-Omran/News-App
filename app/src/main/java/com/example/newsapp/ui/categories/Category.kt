package com.example.newsapp.ui.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//data class describe category then create adapter
@Parcelize
data class Category ( // i write every thing i have in design and take obj from it
    val  id:String,
    val imageId:Int,
    val titleId:Int,        //make it Int because it is dont change
    val backgroundColor:Int,

): Parcelable

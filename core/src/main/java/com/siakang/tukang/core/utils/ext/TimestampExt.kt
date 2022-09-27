package com.siakang.tukang.core.utils.ext

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Timestamp.toDateString(): String {
    val simpleDateFormat = SimpleDateFormat("EEEE, dd MMM yyyy")
    return simpleDateFormat.format(this.toDate())
}
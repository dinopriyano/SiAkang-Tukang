package com.siakang.tukang.domain.usecase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputWrapper(val value: String = "", val error: String? = null) : Parcelable

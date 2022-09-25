package com.siakang.tukang.utils.ext

import com.siakang.tukang.core.data.model.Resource

fun List<Resource<Any>>.findFailure(): Resource.Failure? {
    this.forEach {
        if(it is Resource.Failure) {
            return it
        }
    }

    return null
}
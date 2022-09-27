package com.siakang.tukang.core.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AddressReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoryReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OrderReference
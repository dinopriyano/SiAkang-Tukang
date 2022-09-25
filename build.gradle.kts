buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
    }
}
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks.register(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
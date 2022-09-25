import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    //android ui
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val materialThree = "androidx.compose.material3:material3:${Versions.materialThree}"
    private const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.runtimeKtx}"
    private const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    private const val composeUi = "androidx.compose.ui:ui:${Versions.composeVersion}"
    private const val composeUiUtil = "androidx.compose.ui:ui-util:${Versions.composeVersion}"
    private const val composeUiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    private const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilCompose}"
    private const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilCompose}"
    private const val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.accompanistPager}"
    private const val accompanistPagerIndicator = "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanistPager}"
    private const val composeDestination = "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestination}"
    private const val composeDestinationKsp = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestination}"
    private const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemUiController}"
    private const val materialIconExtended = "androidx.compose.material:material-icons-extended:${Versions.composeVersion}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    private const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    private const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavCompose}"
    private const val accompanistInsets = "com.google.accompanist:accompanist-insets:${Versions.accompanistInsets}"
    private const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    private const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    private const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
    private const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    private const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
    private const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    private const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    private const val coroutinePlayService = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutine}"
    private const val dialogCompose = "com.holix.android:bottomsheetdialog-compose:${Versions.dialogCompose}"
    private const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    private const val dataStorePreferences = "androidx.datastore:datastore-preferences:${Versions.dataStorePreferences}"
    private const val dataStorePreferencesCore = "androidx.datastore:datastore-preferences-core:${Versions.dataStorePreferences}"
    private const val picker = "com.github.rektplorer64:picker-kt:${Versions.picker}"
    private const val accompanistPermission = "com.google.accompanist:accompanist-permissions:${Versions.accompanistPermission}"

    //test
    private const val junit = "junit:junit:${Versions.junit}"
    private const val composeUiJunit = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    private const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"

    //debug
    private const val debugUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    private const val testManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"

    val appLibraries = arrayListOf<String>().apply {
        add(coreKtx)
        add(materialThree)
        add(runtimeKtx)
        add(activityCompose)
        add(composeUi)
        add(composeUiUtil)
        add(composeUiTooling)
        add(coilCompose)
        add(coilSvg)
        add(accompanistPager)
        add(accompanistPagerIndicator)
        add(accompanistPermission)
        add(composeDestination)
        add(systemUiController)
        add(materialIconExtended)
        add(viewModel)
        add(hilt)
        add(hiltNavigationCompose)
        add(accompanistInsets)
        add(firebaseAuth)
        add(firebaseFirestore)
        add(firebaseStorage)
        add(splashScreen)
        add(coroutineCore)
        add(coroutineAndroid)
        add(coroutinePlayService)
        add(dialogCompose)
        add(lottie)
        add(dataStorePreferences)
        add(dataStorePreferencesCore)
        add(activityKtx)
    }

    val platformLibraries = arrayListOf<String>().apply {
        add(firebaseBom)
    }

    val kspLibraries = arrayListOf<String>().apply {
        add(composeDestinationKsp)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJunit)
        add(espresso)
        add(composeUiJunit)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }

    val debugLibraries = arrayListOf<String>().apply {
        add(debugUiTooling)
        add(testManifest)
    }

}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.ksp(list: List<String>) {
    list.forEach { dependency ->
        add("ksp", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.implementationPlatform(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", platform(dependency))
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
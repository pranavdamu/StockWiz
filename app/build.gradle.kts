plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {

    namespace = "com.example.stockwiz_prototype"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.stockwiz_prototype"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation ("com.opencsv:opencsv:5.6")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit for network calls
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // RecyclerView for displaying news items
    implementation (libs.androidx.recyclerview)

    // Lifecycle extensions for managing UI components in accordance with lifecycle states
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx.v231)
    implementation (libs.androidx.lifecycle.livedata.ktx.v231)
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.7") //will alert if memory leak
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //room dependencies
    val room = "2.6.1"

    implementation("androidx.room:room-runtime:$room")
    annotationProcessor("androidx.room:room-compiler:$room")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

}






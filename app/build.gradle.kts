plugins {
    id("com.android.application")
//    id("kotlin-kapt")
}

android {
    namespace = "com.example.qlct_n3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.qlct_n3"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.0-alpha02")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // meownav
//    implementation("com.etebarian:meow-bottom-navigation:1.2.0'")
    implementation ("com.google.android.material:material:1.10.0")
    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
//    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0-rc01")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0-rc01")
    // kotlin extensions and coroutines support for room
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
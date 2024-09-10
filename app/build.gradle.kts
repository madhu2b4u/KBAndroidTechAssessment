plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.example.kbandroidtechassessment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kbandroidtechassessment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.hilt.android.testing)
    kapt (libs.hilt.android.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation("junit:junit:4.13.2")

    // Mockito for mocking
    testImplementation("org.mockito:mockito-core:4.2.0")

    // Mockito Kotlin extension for easier usage with Kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // Truth for easier assertions
    testImplementation("com.google.truth:truth:1.1.3")

    // Hilt for Dependency Injection in tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")

    // Coroutines test library
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Hilt testing requires AndroidX Test libraries
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    // For running tests on AndroidX components
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    // AndroidX Test Runner for running tests
    androidTestImplementation("androidx.test:runner:1.4.0")

    testImplementation("org.robolectric:robolectric:4.7.3")

    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")


}

// Enable kapt for annotation processing (for Hilt)
kapt {
    correctErrorTypes = true
}
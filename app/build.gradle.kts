plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id("kotlin-kapt")
    //id("dagger.hilt.android.plugin")
}

android {
    namespace = "mx.ipn.escom.tta047"
    compileSdk = 34

    defaultConfig {
        applicationId = "mx.ipn.escom.tta047"
        minSdk = 28
        targetSdk = 33
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
        allWarningsAsErrors = false
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.3.0-alpha06")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit GSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit Scalar
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    // Retrofit with moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //GSON
    implementation ("com.google.code.gson:gson:2.8.6")
    // coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")
    //dagger hilt
    //implementation ("com.google.dagger:hilt-android:2.48")
    //kapt ("com.google.dagger:hilt-android-compiler:2.48")
    //Room
    //implementation ("androidx.room:room-ktx:2.4.0")
    //kapt ("androidx.room:room-compiler:2.4.0")
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    // Fragment
    implementation ("androidx.fragment:fragment-ktx:1.3.2")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    // Activity
    implementation ("androidx.activity:activity-ktx:1.2.2")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")
    // WebView
    implementation("com.google.accompanist:accompanist-webview:0.31.3-beta")
    // Extended icons
    implementation("androidx.compose.material:material-icons-extended:1.6.6")

    // Amplify core dependency
    implementation ("com.amplifyframework:core:2.14.11")
    // support for kotlin coroutines
    implementation ("com.amplifyframework:core-kotlin:2.14.11")
    // Amplify Auth
    implementation ("com.amplifyframework:aws-auth-cognito:2.14.11")

    // Support for Java 8 features
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // JWT
    implementation("io.github.nefilim.kjwt:kjwt-core:0.9.0")

    // Youtube video player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")

}
import java.util.Properties
import java.io.FileInputStream

val properties = Properties().apply {
    val propertiesFile = rootProject.file("secrets.properties")
    if (propertiesFile.exists()) {
        load(FileInputStream(propertiesFile))
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.studyapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.studyapp"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    defaultConfig {
        /*
        -- How to use in code --
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
         */
        buildConfigField("String", "SUPABASE_URL", "\"${properties.getProperty("SUPABASE_URL")}\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"${properties.getProperty("SUPABASE_ANON_KEY")}\"")
    }
}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    val fragmentVersion = "1.8.5"
    implementation("androidx.fragment:fragment-ktx:${fragmentVersion}")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    val nav_version = "2.9.8"
    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:${nav_version}")
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:${nav_version}")
    implementation("androidx.navigation:navigation-ui:${nav_version}")
    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Dependency Injection
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.kotlinx.serialization)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    // Preferences DataStore (SharedPreferences like APIs)
    implementation("androidx.datastore:datastore-preferences:1.2.1")
    // Alternatively - without an Android dependency.
    implementation("androidx.datastore:datastore-preferences-core:1.2.1")

    val room_version = "2.8.4"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
}

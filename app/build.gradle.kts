plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.perpustakaan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.perpustakaan"
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

    // Since we are using Kotlin 2.0 with the new compose plugin, 
    // we might typically rely on the plugin defaults, but the user snippet
    // included composeOptions with kotlinCompilerExtensionVersion.
    // However, for Kotlin 2.0 + org.jetbrains.kotlin.plugin.compose,
    // the kotlinCompilerExtensionVersion is DEPRECATED/Not used in the same way 
    // (it uses the Kotlin version).
    // BUT the user explicitly asked for:
    // composeOptions {
    //    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    // }
    // AND provided `composeCompiler = "1.5.15"` in TOML.
    // This is checking if they are using the OLD Compose Compiler (which needs matching Kotlin version) vs the NEW one (bundled with Kotlin).
    // The plugin `org.jetbrains.kotlin.plugin.compose` IS the new one (Compose Compiler 2.0.0+ effectively).
    // Setting `kotlinCompilerExtensionVersion` here might be ignored or cause a warning with the new plugin, 
    // OR it might be required if we were using the old `com.android.tools.build:gradle` compose support.
    // The user's plugin block has `alias(libs.plugins.kotlin.compose)`. This IS the new plugin.
    // The new plugin doesn't use `kotlinCompilerExtensionVersion` in `composeOptions`. It's configured via `composeCompiler { ... }` extension.
    // However, I must follow the user's snippet.
    // If I paste it exactly, it might error if the property is removed in AGP for this setup?
    // Actually, AGP 8.1.2 (requested) still has `composeOptions`.
    // I will include it as requested, but I suspect it's redundant/incorrect for Kotlin 2.0 plugin usage.
    // User said: "Fix the current build error... mandatory".
    // "One of the errors was: Starting in Kotlin 2.0, the Compose Compiler Gradle plugin is required..."
    // So using the plugin IS the fix.
    // I will paste the code as requested.
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)

    implementation(libs.navigation.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}
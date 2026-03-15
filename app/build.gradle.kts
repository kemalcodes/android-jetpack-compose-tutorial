plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kemalcodes.composetutorial"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.kemalcodes.composetutorial"
        minSdk = 24
        targetSdk = 36

        // Version info for the Play Store
        // Increment versionCode for every release (Play Store requires it to increase)
        versionCode = 1
        // Human-readable version shown to users (e.g., "1.0.0", "1.1.0")
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Signing config for release builds
    // Uncomment and fill in with your keystore details before publishing.
    // Never commit your actual keystore passwords to version control!
    //
    // signingConfigs {
    //     create("release") {
    //         storeFile = file("path/to/your/keystore.jks")
    //         storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
    //         keyAlias = System.getenv("KEY_ALIAS") ?: ""
    //         keyPassword = System.getenv("KEY_PASSWORD") ?: ""
    //     }
    // }

    buildTypes {
        release {
            // Enable minification for smaller APK and code obfuscation
            isMinifyEnabled = true
            // Enable resource shrinking to remove unused resources
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Uncomment to use your signing config for release builds
            // signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

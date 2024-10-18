import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.dagger.hilt.android)

    id("kotlin-kapt")
}

android {
    namespace = "com.example.dao"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dao"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val configVariables = arrayOf(
            "INFURA_API_KEY", "RPC_URL", "TOKEN_CONTRACT_ADDRESS", "GOVERNOR_CONTRACT_ADDRESS",
            "LOCAL_RPC_URL", "BLOCK_TIME", "CHAIN_ID"
        )
        for (variable in configVariables)
            buildConfigField("String", variable, "\"${getConfigVariable(variable)}\"")
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "**/*"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Web3j
    implementation(libs.core)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.firestore)
    kapt(libs.hilt.android.compiler)

    // Metamask
    implementation(libs.metamask.android.sdk)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

fun getConfigVariable(key: String): String {
    val properties = Properties()
    project.file("../local.properties").inputStream().let{ properties.load(it) }
    return properties.getProperty(key, "")
}

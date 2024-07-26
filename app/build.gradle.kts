import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

val tokenPropertiesFile = rootProject.file("api.properties")
val tokenProperties = Properties()

if (tokenPropertiesFile.exists()) {
    tokenProperties.load(tokenPropertiesFile.inputStream())
} else {
    throw GradleException("Missing token.properties file")
}

android {
    namespace = "com.jcb.koinapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jcb.koinapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_TOKEN", "\"${tokenProperties["API_TOKEN"]}\"")
        buildConfigField("String", "API_URL", "\"${tokenProperties["API_URL"]}\"")
        buildConfigField("String", "IMAGE_URL", "\"${tokenProperties["IMAGE_URL"]}\"")
        buildConfigField("String", "YOUTUBE_URL", "\"${tokenProperties["YOUTUBE_URL"]}\"")
        buildConfigField(
            "String",
            "YOUTUBE_IMAGE_URL_PRE",
            "\"${tokenProperties["YOUTUBE_IMAGE_URL_PRE"]}\""
        )
        buildConfigField(
            "String",
            "YOUTUBE_IMAGE_URL_POST",
            "\"${tokenProperties["YOUTUBE_IMAGE_URL_POST"]}\""
        )
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.koin.test)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.main)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit.okhttp)
    implementation(libs.retrofit.okhttp.interceptor)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)

    implementation(libs.lifecycle.viewmodel.compose)
}

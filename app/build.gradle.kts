plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.iesaguadulce.lopez_salazar_mario_pmdm02"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.iesaguadulce.lopez_salazar_mario_pmdm02"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"
        resValue("string", "developer_name", "Mario López Salazar")

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

    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation (libs.picasso)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.preference)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
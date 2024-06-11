plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "nlu.hmuaf.android_bookapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "nlu.hmuaf.android_bookapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.circleimageview)
//    implementation(libs.recyclerview)
//    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    depen của step view
    implementation("com.github.shuhart:stepview:1.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.11.0")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // json
    implementation("com.fasterxml.jackson.core:jackson-core:2.7.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.7.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.7.2")
}

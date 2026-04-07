plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // 作为 Xposed 模块使用务必添加，其它情况可选
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "yby1306.wx_notification_messagingstyle"
    compileSdk = 36

    defaultConfig {
        applicationId = "yby1306.wx_notification_messagingstyle"
        minSdk = 30
        targetSdk = 36
       // versionCode = 0
//        versionName = "0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
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
        buildConfig = true
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // 基础依赖
    implementation("com.highcapable.yukihookapi:api:1.3.0")
    // 推荐使用 KavaRef 作为核心反射 API
    implementation("com.highcapable.kavaref:kavaref-core:1.0.1")
    implementation("com.highcapable.kavaref:kavaref-extension:1.0.1")
    // 作为 Xposed 模块使用务必添加，其它情况可选
    compileOnly("de.robv.android.xposed:api:82")
    // 作为 Xposed 模块使用务必添加，其它情况可选
    ksp("com.highcapable.yukihookapi:ksp-xposed:1.3.0")
    // 将 <version> 替换为您需要的版本，例如 '2.0.0-rc1'
//    implementation("org.luckypray:dexkit:2.0.6")
}
plugins {
    alias(libs.plugins.android.application)      // com.android.application v8.5.1
    alias(libs.plugins.jetbrains.kotlin.android) // org.jetbrains.kotlin.android v1.9.0

    // Ativa o Dagger-Hilt (declarado em settings.gradle.kts)
    alias(libs.plugins.hilt.android)             // com.google.dagger.hilt.android v2.44

    // Ativa o KSP (já “amarrado” à versão 1.9.0-1.0.13 em settings.gradle.kts)
    id("com.google.devtools.ksp")

    // Se for usar processamento com KAPT, basta:
    kotlin("kapt")
}

android {
    namespace   = "com.tcc.money"
    compileSdk  = 35

    defaultConfig {
        applicationId         = "com.tcc.money"
        minSdk                = 24
        targetSdk             = 34
        versionCode           = 1
        versionName           = "1.0"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all { it.useJUnitPlatform() }
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Dagger-Hilt
    implementation(libs.hilt.android) // com.google.dagger:hilt-android:2.44
    kapt        (libs.hilt.compiler)  // com.google.dagger:hilt-compiler:2.44

    // Room (usando KSP para annotation processing)
    implementation(libs.androidx.room.runtime)      // androidx.room:room-runtime:2.6.0
    implementation(libs.androidx.room.ktx)      // androidx.room:room-runtime:2.6.0
    ksp         ("androidx.room:room-compiler:2.6.0")

    // Demais dependências (Retrofit, Gson, OkHttp, etc.)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation(libs.retrofit2.retrofit)     // com.squareup.retrofit2:retrofit:2.9.0
    implementation(libs.converter.gson)          // com.squareup.retrofit2:converter-gson:2.9.0

    implementation(libs.androidx.core.ktx)       // androidx.core:core-ktx:1.15.0
    implementation(libs.androidx.appcompat)      // androidx.appcompat:appcompat:1.7.0
    implementation(libs.material)                // com.google.android.material:material:1.12.0
    implementation(libs.androidx.activity)       // androidx.activity:activity:1.10.1
    implementation(libs.androidx.constraintlayout)// androidx.constraintlayout:constraintlayout:2.2.1

    // Testes unitários e instrumentados
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation(libs.junit)               // junit:junit:4.13.2
    androidTestImplementation(libs.androidx.junit) // androidx.test.ext:junit:1.2.1
    androidTestImplementation(libs.androidx.espresso.core) // androidx.test.espresso:espresso-core:3.6.1
}

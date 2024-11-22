plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")  // Adiciona suporte ao KAPT para Hilt
}

android {
    namespace = "com.example.ecowise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecowise"
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    // Dependências de Jetpack Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))  // Para garantir que as versões estejam corretas
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dependências de Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))  // Para testes com Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dependência para ViewModel e LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    // Dependências adicionais para RecyclerView e Activity KTX
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.7.0")

    // Dependências do Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.4.1")  // Usando kapt para gerar o código do Room
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines (necessário para operações assíncronas)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Dependência do Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.6.0")  // Navegação para Compose

    // Dependências do Hilt para injeção de dependências
    implementation("com.google.dagger:hilt-android:2.44")  // Hilt Android
    kapt("com.google.dagger:hilt-android-compiler:2.44")  // Hilt KAPT para compilação
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")  // Hilt com Navigation Compose
}

kapt {
    correctErrorTypes = true  // Necessário para o Hilt
}


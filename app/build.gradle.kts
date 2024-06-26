import com.skydoves.androidveil.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.android.application.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
}

android {
  compileSdk = Configuration.compileSdk
  namespace = "com.skydoves.androidveildemo"

  defaultConfig {
    applicationId = "com.skydoves.androidveildemo"
    minSdk = Configuration.minSdk
    targetSdk = Configuration.targetSdk
    versionCode = Configuration.versionCode
    versionName = Configuration.versionName
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    viewBinding = true
  }

  lint {
    abortOnError = false
  }
}

dependencies {
  implementation(libs.androidx.material)
  implementation(project(":androidveil"))
}


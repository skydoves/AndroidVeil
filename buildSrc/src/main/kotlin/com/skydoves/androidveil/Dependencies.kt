package com.skydoves.androidveil

object Versions {
  internal const val ANDROID_GRADLE_PLUGIN = "7.2.1"
  internal const val ANDROID_GRADLE_SPOTLESS = "6.7.0"
  internal const val GRADLE_NEXUS_PUBLISH_PLUGIN = "1.1.0"
  internal const val KOTLIN = "1.7.10"
  internal const val KOTLIN_GRADLE_DOKKA = "1.7.10"
  internal const val KOTLIN_BINARY_VALIDATOR = "0.11.0"

  internal const val APPCOMPAT = "1.5.0"
  internal const val MATERIAL = "1.6.1"
  internal const val RECYCLERVIEW = "1.2.1"
  internal const val SHIMMER = "0.5.0"
}

object Dependencies {
  const val androidGradlePlugin =
    "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
  const val gradleNexusPublishPlugin =
    "io.github.gradle-nexus:publish-plugin:${Versions.GRADLE_NEXUS_PUBLISH_PLUGIN}"
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
  const val spotlessGradlePlugin =
    "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"
  const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.KOTLIN_GRADLE_DOKKA}"
  const val kotlinBinaryValidator =
    "org.jetbrains.kotlinx:binary-compatibility-validator:${Versions.KOTLIN_BINARY_VALIDATOR}"

  const val appcompat = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
  const val material = "com.google.android.material:material:${Versions.MATERIAL}"
  const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
  const val shimmer = "com.facebook.shimmer:shimmer:${Versions.SHIMMER}"
}

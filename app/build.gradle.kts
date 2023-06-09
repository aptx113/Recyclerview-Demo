/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import BuildTypeDebug.isMinifyEnabled
import dependencyLibs.AndroidTestLibraries.androidTestLibraries
import dependencyLibs.Libraries.kaptLibraries
import dependencyLibs.Libraries.libraries
import dependencyLibs.TestLibraries.testLibraries
import ext.addAndroidTestDependencies
import ext.addDependencies
import ext.addKapt
import ext.addTestDependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")

            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
        }

        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.APPLICATION_ID_SUFFIX
            versionNameSuffix = BuildTypeDebug.VERSION_NAME_SUFFIX
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

    flavorDimensions(BuildProductDimensions.ENVIRONMENT)
//    productFlavors {
//        ProductFlavorDevelop.appCreate(this)
//        ProductFlavorQA.appCreate(this)
//        ProductFlavorProduction.appCreate(this)
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        lintConfig = rootProject.file(".lint/config.xml")
    }

    packagingOptions {
        exclude("META-INF/metadata.kotlin_module")
        exclude("META-INF/metadata.jvm.kotlin_module")
        exclude("META-INF/kotlinx-metadata-jvm.kotlin_module")
        exclude("META-INF/elements.kotlin_module")
        exclude("META-INF/kotlinx-metadata.kotlin_module")
        exclude("META-INF/core.kotlin_module")
        exclude("META-INF/specs.kotlin_module")
    }

    sourceSets {
        val sharedTestDir = "src/sharedTest/java"
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
            java.srcDir(sharedTestDir)
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
            java.srcDir(sharedTestDir)
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    debugImplementation(dependencyLibs.Fragment.FRAG_TEST)
    kaptAndroidTest(dependencyLibs.Hilt.KAPT_TEST)
    addDependencies(libraries)
    addKapt(kaptLibraries)
    addTestDependencies(testLibraries)
    addAndroidTestDependencies(androidTestLibraries)
}

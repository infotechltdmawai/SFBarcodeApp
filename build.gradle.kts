buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jcenter.bintray.com")
        }
        maven {
            url = uri("https://maven.google.com")
        }
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.0")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}// To// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
}
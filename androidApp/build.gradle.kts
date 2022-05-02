import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.paging:paging-runtime:3.0.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")

    add(
        PLUGIN_CLASSPATH_CONFIGURATION_NAME,
        "androidx.compose.compiler:compiler:${Versions.compose}"
    )
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.placeholderMaterial)
    implementation(Compose.coilCompose)
    implementation(Compose.constraintCompose)
    implementation(Compose.pagerAccompanist)
    implementation(Compose.composeMap)

    implementation(Koin.android)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    implementation(Hilt.hiltCompose)

    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.maps.android:maps-ktx:2.3.0")

    testImplementation(Test.turbine)
    testImplementation(Test.kotestAndroid)
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation(Test.coroutinesTest)
    testImplementation(Test.junit)

    //Firebase
    implementation("com.google.firebase:firebase-bom:29.0.4")

    compileOnly("io.realm.kotlin:library-base:0.10.0")

    implementation("com.russhwolf:multiplatform-settings:0.8.1")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.craftie.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        val key = "MAPS_API_KEY"
        manifestPlaceholders["googleMapsApiKey"] = if (System.getenv(key).isNullOrEmpty()) fetchLocalApiKey() else System.getenv(key)
        buildConfigField("String", "USERNAME", usernamePassword().first)
        buildConfigField("String", "PASSWORD", usernamePassword().second)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }

    lint {
        isAbortOnError = false
    }

}

fun fetchLocalApiKey(): String {
    val local = Properties()
    local.load(rootProject.file("./secrets.properties").inputStream())

    val mapsApiKeyId = "MAPS_API_KEY"

    return local[mapsApiKeyId] as String
}

fun usernamePassword(): Pair<String, String> {
    val local = Properties()
    local.load(rootProject.file("./secrets.properties").inputStream())
    val username = local["USERNAME"] as String
    val password = local["PASSWORD"] as String
    return Pair(username, password)
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}
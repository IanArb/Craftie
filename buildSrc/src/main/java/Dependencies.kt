object Versions {
    const val kotlin = "1.6.10"
    const val kotlinCoroutines = "1.6.1"
    const val ktor = "2.0.1"
    const val kotlinxSerialization = "1.3.3"

    const val koin = "3.2.0-beta-1"

    const val junit = "4.13.2"

    const val compose = "1.1.1"
    const val accompanist = "0.24.3-alpha"
    const val nav_compose = "2.4.2"
    const val coil_compose = "1.4.0"
    const val constraintCompose = "1.0.0-beta02"

    const val turbine = "0.8.0"

    const val hilt = "2.42"
    const val hiltCompose = "1.0.0"

    const val mockK = "1.12.4"
    const val kotest = "5.3.0"

    const val realm = "1.0.1"

    const val dateTimeKotlin = "0.3.1"

    const val multiplatformPaging = "0.4.7"
}

object Ktor {
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

    const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
}

object Serialization {
    val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
}

object Koin {
    val core = "io.insert-koin:koin-core:${Versions.koin}"
    val android = "io.insert-koin:koin-android:${Versions.koin}"
    val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val placeholderMaterial = "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanist}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil_compose}"
    const val constraintCompose = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintCompose}"
    const val pagerAccompanist = "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
    const val composeMap = "com.google.maps.android:maps-compose:2.0.0"
}

object Test {
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val mockK = "io.mockk:mockk:${Versions.mockK})"
    const val kotestAndroid = "io.kotest:kotest-framework-engine:${Versions.kotest}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    const val junit = "junit:junit:${Versions.junit}"
}

object Hilt {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}"
}

object Realm {
    const val realmLibrary = "io.realm.kotlin:library-base:${Versions.realm}"
}

object DateTime {
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.dateTimeKotlin}"
}

object Multiplatform {
    const val multiplatformPaging = "io.github.kuuuurt:multiplatform-paging:${Versions.multiplatformPaging}"
}
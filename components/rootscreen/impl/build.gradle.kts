plugins {
    id("flipper.android-compose")
    id("flipper.anvil")
}

android.namespace = "com.flipperdevices.rootscreen.impl"

dependencies {
    implementation(projects.components.rootscreen.api)

    implementation(projects.components.core.di)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.decompose)

    implementation(projects.components.firstpair.api)
    implementation(projects.components.bottombar.api)
    implementation(projects.components.updater.api)
    implementation(projects.components.screenstreaming.api)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.bundles.decompose)
}
// Top-level build file. Plugin versions are declared here (apply false)
// and applied in the :app module. Versions live in gradle/libs.versions.toml.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.compose.compiler) apply false
}

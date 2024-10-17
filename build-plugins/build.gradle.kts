plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("build-jvm") {
            id = "build-jvm"
            implementationClass = "io.goblingamble.build.plugins.JvmBuildPlugin"
        }
        register("build-kmp") {
            id = "build-kmp"
            implementationClass = "io.goblingamble.build.plugins.KmpBuildPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
//    implementation(libs.plugin.mavenPublish)
}

group = "io.goblingamble"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version
}
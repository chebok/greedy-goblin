package io.greedy.goblin.build.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories

@Suppress("unused")
internal class JvmBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) =
        with(project) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
//        pluginManager.apply(KotlinPlatformJvmPlugin::class.java)
            group = rootProject.group
            version = rootProject.version
            repositories {
                mavenCentral()
            }
        }
}

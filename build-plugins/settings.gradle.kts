rootProject.name = "build-plugins"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/version-catalogs/libs.versions.toml"))
        }
    }
}

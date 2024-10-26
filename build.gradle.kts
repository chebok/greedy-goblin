tasks {
    create("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("greedy-goblin-be").task(":check"))
    }
}

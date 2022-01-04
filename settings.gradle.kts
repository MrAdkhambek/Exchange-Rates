rootProject.name = "Exchange Rate"

buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

include(
    ":app"
)
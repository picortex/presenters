plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
    signing
}

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // api(projects.kaseBuilders)
                api(projects.cinematicSceneCore)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.kommanderCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentExecutorsMock)
                implementation(projects.cinematicLiveTest)
                implementation(projects.symphonyCollectionsRenderersConsole)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library"
)
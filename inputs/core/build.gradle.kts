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
                api(projects.kevlarCore) // because forms needs submit actions
                // api(projects.kaseBuilders) // becuase forms has states
                // api(projects.symphonyCollectionsRenderersString) // because form needs to print the table
                api(projects.cinematicSceneCore) // because a form is a viewmodel
                api(kotlinx.serialization.json) // because forms need to serialize
                api(projects.liquidNumber) // because number inputs might need to be formatted
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.symphonyCollectionsRenderersConsole)
                implementation(projects.kommanderCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentExecutorsMock)
                implementation(projects.cinematicLiveTest)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library for headless input fields"
)
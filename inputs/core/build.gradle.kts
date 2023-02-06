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
                api(projects.actionsCore) // because forms needs submit actions
                api(projects.kaseBuilders) // becuase forms has states
                api(projects.presentersCollectionsRenderersString) // because form needs to print the table
                api(projects.viewmodelCore) // because a form is a viewmodel
                api(kotlinx.serialization.json) // because forms need to serialize
                api(projects.formatterCore) // because number inputs might need to be formatted
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.presentersCollectionsRenderersConsole)
                implementation(projects.expectCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentPrimitivesMock)
                implementation(projects.liveTest)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library for headless input fields"
)
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
                api(projects.actionsCore)
                api(projects.viewmodelCore)
                api(projects.identifierCore)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.expectCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentPrimitivesMock)
                implementation(projects.liveTest)
//                implementation(projects.presentersMock)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library"
)
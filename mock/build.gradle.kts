plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
    signing
}

val tmp = 0

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.presentersCore)
                api(projects.liveTest)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.expectCoroutines)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library"
)
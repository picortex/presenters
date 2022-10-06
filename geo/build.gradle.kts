plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
}

val tmp = 2

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.presentersCore)
                api(projects.geoCore)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.presentersMock)
                implementation(projects.expectCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.liveTest)
                implementation(projects.koncurrentPrimitivesMock)
            }
        }
    }
}

aSoftOSSLibrary(
    version = asoft.versions.root.get(),
    description = "A kotlin multiplatform library"
)
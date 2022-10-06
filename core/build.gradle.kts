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
                api(projects.presentersActions)

                api(projects.viewmodelCore)
                api(projects.identifierCore)
                api(projects.kashMoney)

                api(kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.expectCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentPrimitivesMock)
                implementation(projects.liveTest)
                implementation(projects.presentersMock)
            }
        }
    }
}
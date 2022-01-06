plugins {
    kotlin("jvm")
    java
    id("com.squareup.sqldelight")
}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    database("MyDatabase") { // This will be the name of the generated database class.
        packageName = "mr.adkhambek"
    }
}

dependencies {
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
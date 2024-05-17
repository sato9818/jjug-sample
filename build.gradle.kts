plugins {
    id("java")
    id("build.buf") version "0.9.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.google.protobuf:protobuf-bom:3.25.1"))
    implementation("com.google.protobuf:protobuf-java")
}

tasks.test {
    useJUnitPlatform()
}
import build.buf.gradle.BUF_BUILD_DIR
import build.buf.gradle.GENERATED_DIR

plugins {
    id("java")
    id("build.buf") version "0.9.0"
    kotlin("jvm")
}

repositories {
    mavenCentral()
    maven {
        name = "buf"
        url = uri("https://buf.build/gen/maven")
    }
}

dependencies {
    // protocol buffer
    implementation(platform("com.google.protobuf:protobuf-bom:3.25.1"))
    implementation("com.google.protobuf:protobuf-java")
    implementation("com.google.protobuf:protobuf-kotlin")

    // grpc-java
    implementation(platform("io.grpc:grpc-bom:1.62.2"))
    runtimeOnly("io.grpc:grpc-netty-shaded")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-services")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")

    // armeria
    implementation(platform("com.linecorp.armeria:armeria-bom:1.27.0"))
    implementation("com.linecorp.armeria:armeria")
    implementation("com.linecorp.armeria:armeria-grpc")
//    implementation("com.linecorp.armeria:armeria-kotlin")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // reactor
    implementation("io.projectreactor:reactor-core:3.6.2")
    implementation("com.salesforce.servicelibs:reactor-grpc:1.2.4")
    implementation(kotlin("stdlib-jdk8"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    implementation("build.buf.gen:kotsu_proto-share-test_grpc_kotlin:1.4.1.1.20240522080809.e6dd9b3b434b")
    implementation("build.buf.gen:kotsu_proto-share-test_protocolbuffers_kotlin:26.1.0.1.20240522080809.e6dd9b3b434b")
    implementation("build.buf.gen:kotsu_proto-share-test_protocolbuffers_java:26.1.0.1.20240522080809.e6dd9b3b434b")
    implementation("build.buf.gen:kotsu_proto-share-test_grpc_java:1.64.0.1.20240522080809.e6dd9b3b434b")
}

sourceSets {
    val dir = "${layout.buildDirectory.get()}/$BUF_BUILD_DIR/$GENERATED_DIR/java"
    getByName("main").java.srcDir(dir)
    val dir2 = "${layout.buildDirectory.get()}/$BUF_BUILD_DIR/$GENERATED_DIR/kotlin"
    getByName("main").java.srcDir(dir2)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
import build.buf.gradle.BUF_BUILD_DIR
import build.buf.gradle.GENERATED_DIR

plugins {
    id("java")
    id("build.buf") version "0.9.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // ProtoBuf
    implementation(platform("com.google.protobuf:protobuf-bom:3.25.1"))
    implementation("com.google.protobuf:protobuf-java")

    // gRPC-java
    implementation(platform("io.grpc:grpc-bom:1.63.0"))
    runtimeOnly("io.grpc:grpc-netty-shaded")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-services")
    implementation("io.grpc:grpc-stub")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

sourceSets {
    val dir = "${layout.buildDirectory.get()}/$BUF_BUILD_DIR/$GENERATED_DIR/java"
    getByName("main").java.srcDir(dir)
}

tasks.test {
    useJUnitPlatform()
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.2")

    implementation("org.apache.flink:flink-java:1.9.1")
    implementation("org.apache.flink:flink-streaming-java_2.12:1.9.1")
    implementation("org.apache.flink:flink-clients_2.12:1.9.1")
    implementation("org.slf4j:slf4j-simple:1.7.28")
}

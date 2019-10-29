package org.mqtt.producer

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import kotlin.random.Random

fun main() {
    val client = MqttClient("tcp://127.0.0.1:1883", "mqtt-producer", MemoryPersistence())
    client.connect()
    while (true) {
        val nextValue = Random.nextInt(100).toString().toByteArray()
        val message = MqttMessage(nextValue)
        client.publish("/foo", message)
        Thread.sleep(100L)
    }
}

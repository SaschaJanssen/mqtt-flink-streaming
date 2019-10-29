package org.mqtt.source

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.RichSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Simple MQTT source which connects to a local unsecured broker and collects
 * all message from topic `/foo`.
 */
class MqttSource : RichSourceFunction<String>() {

    private var isRunning: AtomicBoolean = AtomicBoolean(false)
    private lateinit var client: MqttClient

    private val mqttBrokerUrl = "tcp://127.0.0.1:1883"
    private val topic = "/foo"

    override fun open(parameters: Configuration) {
        super.open(parameters)

        val connectOptions = MqttConnectOptions()
        connectOptions.isCleanSession = true
        connectOptions.isAutomaticReconnect = true

        client = MqttClient(mqttBrokerUrl, "mqtt-source", MemoryPersistence())
        client.connect(connectOptions)

        isRunning.set(true)
    }

    override fun run(ctx: SourceFunction.SourceContext<String>) {
        client.subscribe(topic, 0) { _, message ->
            ctx.collect(String(message.payload))
        }

        while (isRunning.get()) {
            Thread.sleep(100L)
        }
    }

    override fun cancel() {
        isRunning.set(false)
        this.close()
    }

    override fun close() {
        try {
            client.disconnect()
        } catch (exception: MqttException) {

        } finally {
            super.close()
            this.isRunning.set(false)
        }
    }
}

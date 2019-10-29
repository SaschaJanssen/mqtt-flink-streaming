package org.mqtt

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.mqtt.source.MqttSource

/**
 * Executes in a local flink environment (not a remote cluster env).
 * Data will be processed in 5sec windows, the average of the data within a window is
 * calculated and printed to the console (simple sink).
 */
fun main() {
    val env = StreamExecutionEnvironment.createLocalEnvironment()

    val mqttSource = MqttSource()
    val dataSource = env.addSource(mqttSource)

    dataSource
        .map(MapFunction<String, Int> { value -> value?.toInt() ?: 0 })
        .timeWindowAll(Time.seconds(5))
        .aggregate(AvgAggregate())
        .print()

    env.execute("Mqtt Data Analysis")
}

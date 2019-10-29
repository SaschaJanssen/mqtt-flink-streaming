package org.mqtt

import org.apache.flink.api.common.functions.AggregateFunction

/**
 * Calculates the avverage of the incoming messages.
 */
class AvgAggregate : AggregateFunction<Int, List<Int>, Double> {
    override fun getResult(accumulator: List<Int>): Double = accumulator.average()
    override fun createAccumulator(): List<Int> = emptyList()
    override fun add(value: Int, accumulator: List<Int>): List<Int> = accumulator + value
    override fun merge(a: List<Int>, b: List<Int>): List<Int> = a + b
}

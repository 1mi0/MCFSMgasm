package net.mi0.mcfsmgasm

import net.minikloon.fsmgasm.StateSeries
import org.junit.jupiter.api.Test

class SeriesTest {

    @Test
    fun runTest() {
        val series = object : StateSeries() {
            init {
                addAll(List(10) {
                    TestLoggedState((it + 1).toString(), 2)
                })
            }
        }

        series.start()
        while (!series.ended) {
            series.update()
        }
    }

}
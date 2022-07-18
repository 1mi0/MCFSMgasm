package net.mi0.mcfsmgasm

import net.minikloon.fsmgasm.StateSelfSustaining
import net.minikloon.fsmgasm.State
import org.junit.jupiter.api.Test

class TestLoggedState(private val name: String, override val duration: Int) : State() {
    override fun onStart() = println("$name: Started")
    override fun onUpdate() = println("$name: Updated")
    override fun onEnd() = println("$name: Ended")
}

object StateSelfSustainingTest {
    val selfSustaining = object : StateSelfSustaining() {
        var counter = 1
        override fun factory(): State {
            if (counter == 10) {
                last = true
            }
            return TestLoggedState(counter++.toString(), 2)
        }
    }

    @Test
    fun testSelfState() {
        selfSustaining.start()
        while (!selfSustaining.ended) {
            selfSustaining.update()
        }
    }
}
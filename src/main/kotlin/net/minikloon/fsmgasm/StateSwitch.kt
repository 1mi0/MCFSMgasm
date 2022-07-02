package net.minikloon.fsmgasm

import net.minikloon.fsmgasm.State

open class StateSwitch {
    protected var state: State? = null
    
    fun changeState(next: State) {
        state?.end()
        state = next
        next.start()
    }
    
    fun update() {
        state?.update()
    }
}
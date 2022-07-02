package dev.bozho.states.statelibrary

import net.minikloon.fsmgasm.State

open class StateSeries(states: List<State> = emptyList()) : StateHolder(states) {
    protected var current = 0
    protected var skipping: Boolean = false

    constructor(vararg states: State) : this(states.toList())

    fun addNext(state: State) {
        states.add(current + 1, state)
    }
    
    fun addNext(newStates: List<State>) {
        var i = 1
        newStates.forEach { state ->
            states.add(current + i, state)
            ++i
        }
    }
    
    fun skip() {
        skipping = true
    }
    
    override fun onStart() {
        if(states.isEmpty()) {
            end()
            return
        }
        
        states[current].start()
    }

    override fun onUpdate() {
        states[current].update()
        
        if((states[current].isReadyToEnd() && !states[current].frozen) || skipping) {
            if(skipping)
                skipping = false
            
            states[current].end()

            if(current + 1 >= states.size && isReadyToEnd()) {
                end()
                return
            }

            states[++current].start()
        }
    }

    override fun isReadyToEnd(): Boolean {
        return (current + 1 >= states.size - 1 && states[current].isReadyToEnd() && super.isReadyToEnd())
    }

    override fun onEnd() {
        if(current < states.size)
            states[current].end()
    }

    override val duration: Int = states.fold(0) { curr, state -> curr + state.duration }
}
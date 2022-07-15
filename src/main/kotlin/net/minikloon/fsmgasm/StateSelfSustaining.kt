package net.minikloon.fsmgasm

abstract class StateSelfSustaining : State() {
    protected lateinit var state: State
    var last = false

    override fun onStart() = next()

    override fun onUpdate() {
        state.update()
        if (state.isReadyToEnd()) {
            state.end()
            if (last) {
                end()
                return
            }
            next()
        }
    }

    override fun onEnd() {
        state.end()
    }

    open fun next() {
        if (this::state.isInitialized) {
            state.end()
        }
        state = factory()
        state.start()
    }

    abstract fun factory(): State

    override val duration: Int = infiniteDurationState
}

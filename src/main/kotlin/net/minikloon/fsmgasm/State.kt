package net.minikloon.fsmgasm

import org.apache.logging.log4j.LogManager


val logger = LogManager.getLogger()
// Keep this one above 0. Don't be a dumbass, the code relies on this being above 0
const val infiniteDurationState: Int = Int.MAX_VALUE

abstract class State {
    var started: Boolean = false
        private set
    
    var ended: Boolean = false
        private set

    open var frozen: Boolean = false // prevents the state from ending
    
    private var durationPassed: Int = 0
    private val lock = Any()
    
    open fun start() {
        synchronized(lock) {
            if(started || ended)
                return
            started = true
        }
        
        durationPassed = 0
        try {
            onStart()
        } catch(e: Throwable) {
            logger.error("Exception during ${e.javaClass.name} start")
        }
    }
    
    protected abstract fun onStart()
    
    private var updating = false
    open fun update() {
        synchronized(lock) {
            if(!started || ended || updating)
                return
            updating = true
        }

        durationPassed++
        if(isReadyToEnd() && !frozen) {
            end()
            return
        }
        
        try {
            onUpdate()
        } catch(e: Throwable) {
            logger.error("Exception during ${e.javaClass.name} update")
        }
        updating = false
    }
    
    abstract fun onUpdate()

    open fun end() {
        synchronized(lock) {
            if(!started || ended)
                return
            ended = true
        }
        
        try {
            onEnd()
        } catch(e: Throwable) {
            logger.error("Exception during ${javaClass.name} end")
        }
    }

    open fun isReadyToEnd() : Boolean {
        return ended || (remainingDuration <= 0 && remainingDuration != infiniteDurationState)
    }
    
    protected abstract fun onEnd()
    
    abstract val duration: Int
    
    val remainingDuration: Int
        get() {
            val remaining = duration - durationPassed
            return if (remaining < 0) 0 else remaining
        }
}
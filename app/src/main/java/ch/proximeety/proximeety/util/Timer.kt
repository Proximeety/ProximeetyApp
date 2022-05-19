package ch.proximeety.proximeety.util

import kotlinx.coroutines.*

abstract class Timer(
    private val durationMillis: Long,
    private val tickIntervalMillis: Long,
) {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private var timer: Job? = null

    fun start() {
        timer?.cancel()
        timer = scope.launch {
            var tickCount = 0
            val neededTicks = durationMillis / tickIntervalMillis
            while (tickCount < neededTicks) {
                delay(tickIntervalMillis)
                onTick(durationMillis - tickCount * tickIntervalMillis)
                tickCount += 1
            }
            onFinish()
        }
    }

    fun cancel() {
        timer?.cancel()
    }

    abstract fun onTick(millisUntilFinished: Long)
    abstract fun onFinish()
}
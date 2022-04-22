package ch.proximeety.proximeety.util

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start * (1 - fraction) + stop * fraction
}


package dev.rochajg

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .eagerInitSingletons(true)
        .eagerInitConfiguration(true)
        .start()
}

package com.craftie

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
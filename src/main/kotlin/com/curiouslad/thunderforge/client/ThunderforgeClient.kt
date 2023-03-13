package com.curiouslad.thunderforge.client
import net.fabricmc.api.ClientModInitializer

object ThunderforgeClient: ClientModInitializer {
    override fun onInitializeClient() {
        println("Hello from client!")
    }
}
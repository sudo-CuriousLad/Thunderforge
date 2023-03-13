package com.curiouslad.thunderforge.client
import com.curiouslad.thunderforge.client.registry.BlockEntityRendererRegistry
import net.fabricmc.api.ClientModInitializer

object ThunderforgeClient: ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererRegistry().register()
        println("Hello from client!")
    }
}
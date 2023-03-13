package com.curiouslad.thunderforge
import com.curiouslad.thunderforge.registry.BlockRegistry
import com.curiouslad.thunderforge.registry.ItemRegistry
import net.fabricmc.api.ModInitializer
@Suppress("UNUSED")
object Thunderforge: ModInitializer {
    private const val MOD_ID = "thunderforge"
    override fun onInitialize() {
        ItemRegistry.register()
        BlockRegistry.register()
        println("Example mod has been initialized.")
    }
}
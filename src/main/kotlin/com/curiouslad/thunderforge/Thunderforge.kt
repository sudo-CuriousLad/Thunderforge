package com.curiouslad.thunderforge
import com.curiouslad.thunderforge.item.StaticStone
import com.curiouslad.thunderforge.registry.ItemRegistry
import com.curiouslad.thunderforge.utils.getIdentifier
import net.fabricmc.api.ModInitializer
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object Thunderforge: ModInitializer {
    private const val MOD_ID = "mod_id"
    override fun onInitialize() {
        println("Example mod has been initialized.")

    }
}
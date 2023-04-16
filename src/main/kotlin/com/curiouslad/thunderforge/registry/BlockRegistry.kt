package com.curiouslad.thunderforge.registry

import com.curiouslad.thunderforge.block.TestBlock
import com.curiouslad.thunderforge.block.multiblocks.controllers.Thunderforge
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockRegistry {
    val TEST_BLOCK = TestBlock()
    val THUNDERFORGE = Thunderforge()
//    val DARKSTONE = Darkstone()

    fun register() {
        Registry.register(Registries.BLOCK, Identifier("thunderforge", "test_block"), TEST_BLOCK)
        Registry.register(Registries.BLOCK, Identifier("thunderforge", "thunderforge"), THUNDERFORGE)
//        Registry.register(Registries.BLOCK, Identifier("thunderforge", "darkstone"), DARKSTONE)
    }
}

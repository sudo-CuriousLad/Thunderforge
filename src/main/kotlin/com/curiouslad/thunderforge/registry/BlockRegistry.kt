package com.curiouslad.thunderforge.registry

import com.curiouslad.thunderforge.block.TestBlock
import com.curiouslad.thunderforge.block.multiblocks.controllers.GhostGenerator
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockRegistry {
    val TEST_BLOCK = TestBlock()
    val GHOST_GENERATOR = GhostGenerator()

    fun register() {
        Registry.register(Registries.BLOCK, Identifier("thunderforge", "test_block"), TEST_BLOCK)
        Registry.register(Registries.BLOCK, Identifier("thunderforge", "ghost_generator"), GHOST_GENERATOR)
    }
}

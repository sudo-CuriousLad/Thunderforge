package com.curiouslad.thunderforge.registry

import com.curiouslad.thunderforge.block.TestBlock
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockRegistry {
    val TEST_BLOCK = TestBlock()

    fun register() {
        Registry.register(Registries.BLOCK, Identifier("thunderforge", "test_block"), TEST_BLOCK)
    }
}

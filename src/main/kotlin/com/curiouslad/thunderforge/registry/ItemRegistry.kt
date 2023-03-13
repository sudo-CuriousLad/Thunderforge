package com.curiouslad.thunderforge.registry

import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ItemRegistry {
    fun register() {
        Registry.register(Registries.ITEM, Identifier("thunderforge", "test_block_item"), BlockItem(BlockRegistry.TEST_BLOCK, Item.Settings()))
    }
}
package com.curiouslad.thunderforge.client.registry

import com.curiouslad.thunderforge.block.entity.renderers.TestBlockEntityRenderer
import com.curiouslad.thunderforge.registry.BlockEntityRegistry
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

class BlockEntityRendererRegistry {
    fun register() {
        BlockEntityRendererRegistry.register(BlockEntityRegistry.TEST_BLOCK_ENTITY) { TestBlockEntityRenderer() }
    }
}
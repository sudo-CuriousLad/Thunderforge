package com.curiouslad.thunderforge.block.multiblocks.controllers.entity

import com.curiouslad.thunderforge.registry.BlockEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class ThunderforgeBlockEntity(pos: BlockPos?, state: BlockState?) :
    BlockEntity(BlockEntityRegistry.GHOST_GENERATOR_BLOCK_ENTITY, pos, state) {
}
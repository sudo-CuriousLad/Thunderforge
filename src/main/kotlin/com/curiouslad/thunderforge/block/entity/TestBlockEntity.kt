package com.curiouslad.thunderforge.block.entity

import com.curiouslad.thunderforge.registry.BlockEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class TestBlockEntity(pos: BlockPos?, state: BlockState?) : BlockEntity(
    BlockEntityRegistry.TEST_BLOCK_ENTITY, pos,
    state,
) {


}
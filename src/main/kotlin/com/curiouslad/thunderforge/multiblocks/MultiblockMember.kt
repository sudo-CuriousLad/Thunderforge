package com.curiouslad.thunderforge.multiblocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess


//Class for all blocks that will be part of multiblocks.
open class MultiblockMember(settings: Settings): Block(settings) {



    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        //world!!.setBlockState(pos, state!!.with(BooleanProperty.of("formed"), false), 0)
        super.onBroken(world, pos, state)
    }

}
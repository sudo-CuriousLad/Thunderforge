package com.curiouslad.thunderforge.multiblocks

import com.curiouslad.thunderforge.multiblocks.interfaces.BlockPosProperty
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess


//Class for all blocks that will be part of multiblocks.
open class MultiblockMember(settings: Settings): Block(settings) {

    init {
        defaultState = defaultState.with(BlockPosProperty("controller"), BlockPos.ZERO as BlockPos?)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BlockPosProperty("controller"))
    }

    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        world!!.setBlockState(pos, state!!.with(BooleanProperty.of("formed"), false), 0)
        super.onBroken(world, pos, state)
    }

}
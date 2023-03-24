package com.curiouslad.thunderforge.multiblocks.interfaces

import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface MultiblockController {

    val blockArray: Array<Pair<BlockPos, Block>>

    //Checks if the blocks match up and the multi can form.
    fun canForm(world: World?, selfPos: BlockPos): Boolean {
        var blocksPresent = 0

        for (pair in blockArray) {
            if (world!!.getBlockState(selfPos.add(pair.first)).block == pair.second) {
                blocksPresent++
            }
        }

        return blocksPresent == blockArray.size
    }


    //Assigns the controller position to each block in the multiblock's block array.
    fun assignToMulti(world: World?) {
        for (pair in blockArray) {
            val state = world!!.getBlockState(pair.first)
            if (state.properties.contains(BlockPosProperty("controller"))) {
                world!!.setBlockState(pair.first, state.withIfExists(BlockPosProperty("controller"), pair.first))
            }
        }
    }
}
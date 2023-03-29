package com.curiouslad.thunderforge.multiblocks.interfaces

import com.curiouslad.thunderforge.multiblocks.MultiblockMember
import net.minecraft.block.Block
import net.minecraft.state.property.EnumProperty
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

    //Sets the block states of the blocks used in the multi.
    fun setBlockStates(world: World?, controller: MultiblockMember.ThunderforgeMultis) {
        val array = blockArray.filter { it.second is MultiblockMember }

        array.forEach { world!!.setBlockState(it.first,
            world.getBlockState(it.first)
                .withIfExists(EnumProperty.of("current_multi", controller.javaClass), controller)
        )
        }
    }

}
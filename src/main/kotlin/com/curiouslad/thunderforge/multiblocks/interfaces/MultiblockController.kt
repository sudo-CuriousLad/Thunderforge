package com.curiouslad.thunderforge.multiblocks.interfaces

import com.curiouslad.thunderforge.multiblocks.util.CustomBlockPattern
import net.minecraft.registry.RegistryKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World

interface MultiblockController {

    val blockPattern: CustomBlockPattern
    val boundingBox: Box

    //Checks if the blocks match up and the multi can form.
    fun canForm(world: World?, selfPos: BlockPos, dimKey: RegistryKey<World>): CustomBlockPattern.Result? {
        return (blockPattern.searchAround(world!!.server!!.getWorld(dimKey), selfPos))
    }

    fun disableRenderer() {
    }

    //Sets the block states of the blocks used in the multi.

}
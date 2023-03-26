package com.curiouslad.thunderforge.multiblocks.tracker

import com.curiouslad.thunderforge.multiblocks.interfaces.MultiblockTracker
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

class ThunderforgeTracker : MultiblockTracker<BlockPos> {
    override var controllerArray: Array<BlockPos> = emptyArray()
    override var box = Box(BlockPos.ZERO.add(-1, 0, 0) as BlockPos?, BlockPos.ZERO.add(1, 2, 2) as BlockPos?)


}
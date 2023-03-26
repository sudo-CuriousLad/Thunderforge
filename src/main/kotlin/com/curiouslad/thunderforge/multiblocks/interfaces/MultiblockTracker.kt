package com.curiouslad.thunderforge.multiblocks.interfaces

import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3i
import net.minecraft.world.World

interface MultiblockTracker<T: BlockPos> {

    var controllerArray: Array<T>

    var box: Box

    fun checkDeform(world: World?, pos: BlockPos?) {
        for (controller in controllerArray) {
            val relativePos = pos!!.subtract(controller as Vec3i)
            val state = world!!.getBlockState(controller)
            if (box.contains(relativePos.x.toDouble(), relativePos.y.toDouble(), relativePos.z.toDouble())) {
                world.setBlockState(controller, state.withIfExists(BooleanProperty.of("formed"), false))
                break
            }
        }

    }

}
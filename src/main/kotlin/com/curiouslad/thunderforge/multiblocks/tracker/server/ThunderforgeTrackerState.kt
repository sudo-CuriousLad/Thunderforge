package com.curiouslad.thunderforge.multiblocks.tracker.server

import com.curiouslad.thunderforge.multiblocks.tracker.ThunderforgeTracker
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIntArray
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.PersistentState

class ThunderforgeTrackerState : PersistentState() {

    var tracker: ThunderforgeTracker = ThunderforgeTracker()

    override fun writeNbt(nbt: NbtCompound?): NbtCompound {

        val controllerList = NbtList()

        for (i in tracker.controllerArray) {
            tracker.controllerArray.forEach {
                val intArray = IntArray(3) { i ->
                    when (i) {
                        0 -> it.x
                        1 -> it.y
                        2 -> it.z
                        else -> 0
                    }
                }
                controllerList.plus(intArray)
            }
        }

        val boxCoords = arrayOf(-1, 0, 0, 1, 2, 2)

        val boxList = IntArray(6) {
            boxCoords[it]
        }

        nbt!!.put("thunderforgeControllers", controllerList)
        nbt.putIntArray("thunderforgeBox", boxList)
        return nbt
    }

    fun fromNbt(nbt: NbtCompound?) {
        val serverState = ThunderforgeTrackerState()

        var controllerArray: Array<BlockPos> = arrayOf()

        lateinit var box: Box

        val boxCoords = nbt!!.getIntArray("thunderforgeBox")


        nbt!!.getList("thunderforgeControllers", 9).forEach {
            if (it.nbtType == NbtTypes.byId(11)) {
                controllerArray[controllerArray.lastIndex + 1] = (BlockPos.ZERO.add(
                    (it as NbtIntArray).intArray[0], it.intArray[1], it.intArray[2]
                ) as BlockPos)
            }
        }

        serverState.tracker.controllerArray = controllerArray
        serverState.tracker.box = Box(
            boxCoords[0].toDouble(),
            boxCoords[1].toDouble(),
            boxCoords[2].toDouble(),
            boxCoords[3].toDouble(),
            boxCoords[4].toDouble(),
            boxCoords[5].toDouble()
        )


    }

}
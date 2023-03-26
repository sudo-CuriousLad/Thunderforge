package com.curiouslad.thunderforge.multiblocks.tracker.server

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtIntArray
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtTypes
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.PersistentState
import net.minecraft.world.World

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

    private fun fromNbt(nbt: NbtCompound?): ThunderforgeTrackerState {
        val serverState = ThunderforgeTrackerState()

        val controllerArray: Array<BlockPos> = arrayOf()

        nbt!!.getList("thunderforgeControllers", 9).forEach {
            if (it.nbtType == NbtTypes.byId(11)) {
                controllerArray[controllerArray.lastIndex + 1] = (BlockPos.ZERO.add(
                    (it as NbtIntArray).intArray[0], it.intArray[1], it.intArray[2]
                ) as BlockPos)
            }
        }

        return serverState

    }

    fun getServerState(server: MinecraftServer): ThunderforgeTrackerState {

        val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager

        val thunderforgeTrackerState: ThunderforgeTrackerState =persistentStateManager.getOrCreate(
            {ThunderforgeTrackerState().fromNbt(it)},
            { ThunderforgeTrackerState() }, "thunderforge")


        thunderforgeTrackerState.markDirty()

        return  thunderforgeTrackerState

    }

}
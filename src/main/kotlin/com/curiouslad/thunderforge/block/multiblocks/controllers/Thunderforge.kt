package com.curiouslad.thunderforge.block.multiblocks.controllers

import com.curiouslad.thunderforge.block.multiblocks.controllers.entity.ThunderforgeBlockEntity
//import com.curiouslad.thunderforge.block.multiblocks.members.GhostBrick
import com.curiouslad.thunderforge.multiblocks.interfaces.MultiblockController
import com.curiouslad.thunderforge.multiblocks.tracker.server.ThunderforgeTrackerState
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class Thunderforge : MultiblockController, BlockWithEntity(Settings.of(Material.AMETHYST)) {
    override val blockArray: Array<Pair<BlockPos, Block>> = arrayOf(Pair(BlockPos(0, 1, 0), Blocks.AMETHYST_BLOCK))
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        return ThunderforgeBlockEntity(pos, state)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BooleanProperty.of("formed"))
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (player!!.inventory.mainHandStack == Items.AMETHYST_SHARD.defaultStack) {
            if (canForm(world, pos!!)) {
                world!!.setBlockState(pos, state!!.with(BooleanProperty.of("formed"), true))
            }
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun onPlaced(
        world: World?,
        pos: BlockPos?,
        state: BlockState?,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        var thunderforgeTrackerState = ThunderforgeTrackerState().getServerState(world?.server!!)
        thunderforgeTrackerState.tracker.controllerArray.plus(pos!!)
        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        val thunderforgeTrackerState = ThunderforgeTrackerState().getServerState(world!!.server!!)
        val index = thunderforgeTrackerState.tracker.controllerArray.indexOf(pos!!)

        thunderforgeTrackerState.tracker.controllerArray = removeElement(thunderforgeTrackerState.tracker.controllerArray, index)
        super.onBroken(world, pos, state)
    }

    private fun removeElement(arr: Array<BlockPos>, itemIndex: Int): Array<BlockPos> {
        val arrList = arr.toMutableList()

        arrList.removeAt(itemIndex)

        return arrList.toTypedArray()
    }
}
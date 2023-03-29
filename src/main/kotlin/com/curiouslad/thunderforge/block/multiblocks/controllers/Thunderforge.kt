package com.curiouslad.thunderforge.block.multiblocks.controllers

import com.curiouslad.thunderforge.block.multiblocks.controllers.entity.ThunderforgeBlockEntity
import com.curiouslad.thunderforge.multiblocks.MultiblockMember
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

    private var FORMED: BooleanProperty = BooleanProperty.of("formed")

    init {
        defaultState = defaultState.with(FORMED, false)
    }
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return ThunderforgeBlockEntity(pos, state)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BooleanProperty.of("formed"))
    }

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState?,
        world: World,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (player!!.isHolding(Items.AMETHYST_SHARD) && !world.getBlockState(pos).get(FORMED)) {
            if (canForm(world, pos!!)) {
                world.setBlockState(pos, state!!.with(FORMED, true))
                setBlockStates(world, MultiblockMember.ThunderforgeMultis.THUNDERFORGE)
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
        if(world!!.isClient) {
            return
        }
      val thunderforgeTrackerState = ThunderforgeTrackerState().getServerState(world.server!!)
        thunderforgeTrackerState.tracker = thunderforgeTrackerState.tracker.plusElement(pos!!)
        thunderforgeTrackerState.tracker.forEach { println("From thunderforge tracker:$it") }

        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        if(world!!.isClient) {
            return
        }
        val thunderforgeTrackerState = ThunderforgeTrackerState().getServerState(world.server!!)
        val index = thunderforgeTrackerState.tracker.indexOf(pos!!)
        println(index.toString())

        thunderforgeTrackerState.tracker = removeElement(thunderforgeTrackerState.tracker, index)

        println(thunderforgeTrackerState.tracker.toString())
        super.onBroken(world, pos, state)
    }

    override fun neighborUpdate(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        sourceBlock: Block?,
        sourcePos: BlockPos?,
        notify: Boolean
    ) {
        if (sourceBlock!!.javaClass == Blocks.AMETHYST_BLOCK.javaClass) {
            world!!.setBlockState(pos, state!!.with(FORMED, false))
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify)
    }

    private fun removeElement(arr: Array<BlockPos>, itemIndex: Int): Array<BlockPos> {
        val arrList = arr.toMutableList()

        arrList.removeAt(itemIndex)

        return arrList.toTypedArray()
    }
}
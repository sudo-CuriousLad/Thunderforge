package com.curiouslad.thunderforge.block.multiblocks.controllers

//import com.curiouslad.thunderforge.block.multiblocks.members.GhostBrick
import com.curiouslad.thunderforge.block.multiblocks.controllers.entity.ThunderforgeBlockEntity
import com.curiouslad.thunderforge.multiblocks.interfaces.MultiblockController
import com.curiouslad.thunderforge.multiblocks.tracker.server.ThunderforgeTrackerState
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.pattern.BlockPattern
import net.minecraft.block.pattern.BlockPatternBuilder
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.predicate.block.BlockStatePredicate
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.*
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class Thunderforge : MultiblockController, BlockWithEntity(Settings.of(Material.AMETHYST)) {
   override val blockPattern: BlockPattern = BlockPatternBuilder.start().
   aisle(
       "___",
       "_d_",
       "d_d",
       "s_s",
       "sds"
   ).aisle(
       "_d_",
       "d_d",
       "___",
       "_c_",
       "ddd",
   ).
   aisle(
       "___",
       "_d_",
       "d_d",
       "s_s",
       "sds"
   ).
   where('_', CachedBlockPosition.matchesBlockState(BlockStatePredicate.ANY)).
   where('d', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.POLISHED_BLACKSTONE))).
   where('s', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.BLACKSTONE))).
   where('c', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(this))).
   build()

    val blocksUsed = arrayOf(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE)

    override val boundingBox: Box = Box(Vec3d.of(Vec3i(-1, -1, -1)),Vec3d.of(Vec3i(1, 4, 1)))

    private var FORMED: BooleanProperty = BooleanProperty.of("formed")
    private var FACING = DirectionProperty.of("facing")
    private var DISABLE_RENDERER = BooleanProperty.of("disable_renderer")

    init {
        defaultState = defaultState.with(FORMED, false).with(FACING, Direction.NORTH)
    }
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return ThunderforgeBlockEntity(pos, state)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BooleanProperty.of("formed")).add(DirectionProperty.of("facing"))
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
        if (!world.isClient) {
            if (player!!.isHolding(Items.AMETHYST_SHARD) && !world.getBlockState(pos).get(FORMED)) {
                if (canForm(world, pos!!, player.world.registryKey) != null) {
                    world.setBlockState(pos, state!!.with(FORMED, true))
                    val i: Int = Math.max(Math.max(blockPattern.width, blockPattern.height), blockPattern.depth)
                    val iterator: Iterator<BlockPos> = BlockPos.iterate(pos, pos.add(i - 1, i - 1, i - 1)).iterator()

                    iterator.forEach {
                            if(world.getBlockState(it).block in blocksUsed) {
                                world.setBlockState(it, world.getBlockState(it).with(DISABLE_RENDERER, true))
                                world.getBlockState(it).block
                        }
                    }
//                    println(blockPattern.posArray.size)
////                    blockPattern.posArray.forEach {
////                        if(world.getBlockState(it).block in blocksUsed) {
////                            world.setBlockState(it, world.getBlockState(it).with(IN_MULTI, true))
////                        }
////                        println(it.toString())
////                    }
                }
            }
        } else {
            return ActionResult.PASS
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

    @Deprecated("Deprecated in Java")
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

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return super.getPlacementState(ctx)!!.with(FACING, ctx!!.playerFacing.opposite)
    }
}
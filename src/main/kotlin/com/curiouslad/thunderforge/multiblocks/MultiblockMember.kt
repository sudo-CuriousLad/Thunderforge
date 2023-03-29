package com.curiouslad.thunderforge.multiblocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable


//Class for all blocks that will be part of multiblocks.
open class MultiblockMember(settings: Settings): Block(settings) {
//    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
//        //world!!.setBlockState(pos, state!!.with(BooleanProperty.of("formed"), false), 0)
//        super.onBroken(world, pos, state)
//    }
    enum class ThunderforgeMultis: StringIdentifiable {
        UNFORMED {
            override fun asString(): String {
                return "unformed"
            }
        },
        THUNDERFORGE {
            override fun asString(): String {
                return "thunderforge"
            }
        },;
    }

    val CURRENT_MULTI: EnumProperty<ThunderforgeMultis> = EnumProperty.of("current_multi", ThunderforgeMultis.THUNDERFORGE.javaClass)

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(EnumProperty.of("current_multi", ThunderforgeMultis.THUNDERFORGE.javaClass))
        super.appendProperties(builder)
    }

}
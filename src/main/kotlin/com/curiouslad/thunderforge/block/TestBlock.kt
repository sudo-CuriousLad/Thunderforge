package com.curiouslad.thunderforge.block

import com.curiouslad.thunderforge.block.entity.TestBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TestBlock : BlockWithEntity(FabricBlockSettings.of(Material.AMETHYST)) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return TestBlockEntity(pos, state)
    }

    init {
        defaultState = (defaultState.with(BooleanProperty.of("powered"), false))
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BooleanProperty.of("powered"))
    }

    @Deprecated("Deprecated")
    override fun neighborUpdate(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        sourceBlock: Block?,
        sourcePos: BlockPos?,
        notify: Boolean
    ) {
        val isPowered = world?.getReceivedRedstonePower(pos)!! > 0

        world.setBlockState(pos, state?.with(BooleanProperty.of("powered"), isPowered))
    }

}


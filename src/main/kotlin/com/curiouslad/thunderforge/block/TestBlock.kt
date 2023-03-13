package com.curiouslad.thunderforge.block

import com.curiouslad.thunderforge.block.entity.TestBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class TestBlock: BlockWithEntity(FabricBlockSettings.of(Material.AMETHYST)) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        return TestBlockEntity(pos, state)
    }
}


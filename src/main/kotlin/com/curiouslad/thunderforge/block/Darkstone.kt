package com.curiouslad.thunderforge.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty

class Darkstone : Block(Settings.of(Material.AMETHYST).nonOpaque()) {
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!.add(BooleanProperty.of("disable_renderer"))
    }
}
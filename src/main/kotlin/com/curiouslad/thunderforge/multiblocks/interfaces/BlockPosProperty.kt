package com.curiouslad.thunderforge.multiblocks.interfaces

import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import java.util.*

class BlockPosProperty(name: String) : Property<BlockPos>(name, BlockPos::class.java) {
    private var position: BlockPos = BlockPos.ORIGIN

    override fun getValues(): MutableCollection<BlockPos> {
        TODO("Not yet implemented")
    }

    override fun parse(name: String?): Optional<BlockPos> {
        TODO("Not yet implemented")
    }

    override fun name(value: BlockPos?): String {
        TODO("Not yet implemented")
    }

}
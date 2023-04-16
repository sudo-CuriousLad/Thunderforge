package com.curiouslad.thunderforge.multiblocks


//Class for all blocks that will be part of multiblocks.
//open class MultiblockMember(settings: Settings): Block(settings) {
//    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
//        //world!!.setBlockState(pos, state!!.with(BooleanProperty.of("formed"), false), 0)
//        super.onBroken(world, pos, state)
//    }

//    val CURRENT_MULTI: EnumProperty<ThunderforgeMultis> = EnumProperty.of("current_multi", ThunderforgeMultis.UNFORMED.javaClass)
//
//    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
//        builder!!.add(EnumProperty.of("current_multi", ThunderforgeMultis.THUNDERFORGE.javaClass))
//    }

//}
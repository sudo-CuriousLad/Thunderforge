package com.curiouslad.thunderforge.registry

import com.curiouslad.thunderforge.block.entity.TestBlockEntity
import com.curiouslad.thunderforge.block.multiblocks.controllers.entity.ThunderforgeBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockEntityRegistry {
    val TEST_BLOCK_ENTITY: BlockEntityType<TestBlockEntity> = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier("thunderforge", "test_block_entity"),
        FabricBlockEntityTypeBuilder.create({ pos, state -> TestBlockEntity(pos, state) }, BlockRegistry.TEST_BLOCK)
            .build()
    )

    val THUNDERFORGE_BLOCK_ENTITY: BlockEntityType<ThunderforgeBlockEntity> = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier("thunderforge", "thunderforge_block_entity"),
        FabricBlockEntityTypeBuilder.create(
            { pos, state -> ThunderforgeBlockEntity(pos, state) },
            BlockRegistry.THUNDERFORGE
        )
            .build()
    )
}
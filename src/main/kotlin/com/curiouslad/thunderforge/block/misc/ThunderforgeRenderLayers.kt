package com.curiouslad.thunderforge.block.misc

import com.curiouslad.thunderforge.mixins.client.accessors.RenderLayerAccessor
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.Identifier

class ThunderforgeRenderLayers {

    class EmissiveLayer(
        name: String?, vertexFormat: VertexFormat?, drawMode: VertexFormat.DrawMode?,
        expectedBufferSize: Int, hasCrumbling: Boolean, translucent: Boolean, startAction: Runnable?,
        endAction: Runnable?
    ) : RenderLayer(
        name, vertexFormat,
        drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction
    ) {
        companion object {
            fun get(texture: Identifier): RenderLayer {
                val multiPhaseParameters: MultiPhaseParameters =
                    MultiPhaseParameters.builder().texture(Texture(texture, true, false)).transparency(
                        TRANSLUCENT_TRANSPARENCY
                    ).cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP).overlay(DISABLE_OVERLAY_COLOR)
                        .layering(VIEW_OFFSET_Z_LAYERING).program(ENERGY_SWIRL_PROGRAM).build(true)
                return RenderLayerAccessor.invokeOf(
                    "thunderforge_emissive",
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    false,
                    false,
                    multiPhaseParameters
                )
            }
        }
    }

    /*class RegularLayer(
        name: String?, vertexFormat: VertexFormat?, drawMode: VertexFormat.DrawMode?,
        expectedBufferSize: Int, hasCrumbling: Boolean, translucent: Boolean, startAction: Runnable?,
        endAction: Runnable?
    ) : RenderLayer(
        name, vertexFormat,
        drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction
    ) {
        companion object {
            fun get(texture: Identifier): RenderLayer {
                val multiPhaseParameters: MultiPhaseParameters =
                    MultiPhaseParameters.builder().texture(Texture(texture, true, false)).transparency(
                        TRANSLUCENT_TRANSPARENCY
                    ).cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP).overlay(DISABLE_OVERLAY_COLOR)
                        .layering(VIEW_OFFSET_Z_LAYERING).program(POSITION_COLOR_TEXTURE_PROGRAM).build(true)
                return RenderLayerAccessor.invokeOf(
                    "thunderforge_normal",
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    false,
                    false,
                    multiPhaseParameters
                )
            }
        }
    }*/

}
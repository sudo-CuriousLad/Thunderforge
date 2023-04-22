package com.curiouslad.thunderforge.block.entity.renderers

import com.curiouslad.thunderforge.block.entity.TestBlockEntity
import com.curiouslad.thunderforge.block.misc.ThunderforgeRenderLayers
import com.curiouslad.thunderforge.interpolation.LinearSpline
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.Identifier
import org.joml.Vector3d
import kotlin.math.cos
import kotlin.math.sin

@Environment(EnvType.CLIENT)
open class TestBlockEntityRenderer: BlockEntityRenderer<TestBlockEntity> {
    private val texture1 = Identifier("thunderforge", "textures/test_block_spinny.png")
    private val texture2 = Identifier("thunderforge", "textures/test_block_spinny_alt.png")
    private val circle: ModelPart = getTexturedModelData().createModel().getChild("circle")

    private val pathArray: Array<Vector3d> = arrayOf(
        Vector3d(-1.0, -1.0, -1.0),
        Vector3d(1.0, 1.0, 1.0),
        Vector3d(1.0, 1.0, 1.0),
        Vector3d(-1.0, -1.0, -1.0)
    )

    private val interpolator = LinearSpline(posArray = pathArray)

    private fun getTexturedModelData(): TexturedModelData {
        val modelData = ModelData()
        val modelPartData = modelData.root
        modelPartData.addChild("circle", ModelPartBuilder.create(), ModelTransform.pivot(8.0f, 0.1f, 8.0f))
        modelPartData.getChild("circle").addChild(
            "circle2",
            ModelPartBuilder.create().uv(0, 0).cuboid(-32.0f, 0.0f, -29f, 64.0f, 0.0f, 64.0f),
            ModelTransform.rotation(0.0f, 0.0f, 0.0f)
        )
        return TexturedModelData.of(modelData, 64, 64)
    }

    override fun render(
        entity: TestBlockEntity?,
        tickDelta: Float,
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        overlay: Int
    ) {
        val world = entity?.world
        val pos= entity?.pos
        val time = entity?.world!!.time % 50000 + tickDelta
        matrices?.push()
        if (world?.getBlockState(pos)?.properties?.contains(BooleanProperty.of("powered")) == true) {
            val ifPowered = world.getBlockState(pos)?.get(BooleanProperty.of("powered"))
            circle.yaw = time / 25.0f
            circle.pitch = sin(time /8.0f) * Math.toRadians(15.0).toFloat()
            circle.roll = cos(time /4.0f) * Math.toRadians(15.0).toFloat()

            interpolator.transform(matrices!!)

            circle.render(
                matrices,
                vertexConsumers?.getBuffer(
                    ThunderforgeRenderLayers.EmissiveLayer.get(
                        if (ifPowered == true) {
                            texture2
                        } else {
                            texture1
                        }
                    )
                ),
                light,
                overlay
            )
        }
        matrices?.pop()
        }
}
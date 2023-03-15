package com.curiouslad.thunderforge.interpolation

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.BlockPos
import org.joml.Math
import org.joml.Vector3d

class LinearSpline(private val posArray: Array<Vector3d>) {

    fun transform(initPos: BlockPos, obj: MatrixStack, step: Double) {
        posArray.map { vec -> Vector3d(initPos.x.toDouble(), initPos.y.toDouble(), initPos.z.toDouble()).add(vec) }

        var pointer = 0
        var time = 0.0

        while (pointer + 1 < posArray.size) {
            val x1 = posArray[pointer].x
            val y1 = posArray[pointer].y
            val z1 = posArray[pointer].z
            val x2 = posArray[pointer + 1].x
            val y2 = posArray[pointer + 1].y
            val z2 = posArray[pointer + 1].z

            val xMid = Math.lerp(x1, x2, time)
            val yMid = Math.lerp(y1, y2, time)
            val zMid = Math.lerp(z1, z2, time)

            obj.translate(xMid, yMid, zMid)

            pointer++
            time += step
        }

    }

}
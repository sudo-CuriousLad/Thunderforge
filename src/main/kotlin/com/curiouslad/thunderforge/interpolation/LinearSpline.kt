package com.curiouslad.thunderforge.interpolation

import net.minecraft.client.util.math.MatrixStack
import org.joml.Math
import org.joml.Vector3d

class LinearSpline(posArray: Array<Vector3d>, private val initPos: Vector3d) {

    private val posArray = arrayOf(Vector3d(initPos.x, initPos.y, initPos.z)).plus(posArray.map { vec -> Vector3d(
        initPos.x, initPos.y, initPos.z
    ).add(vec) })

    var pointer = 0
    var time = 0.0

    fun transform(obj: MatrixStack, step: Double) {
        if (pointer + 1 < posArray.size) {
            val x1 = posArray[pointer].x
            val y1 = posArray[pointer].y
            val z1 = posArray[pointer].z
            val x2 = posArray[pointer + 1].x
            val y2 = posArray[pointer + 1].y
            val z2 = posArray[pointer + 1].z

            val xMid = Math.lerp(x1, x2, time)
            val yMid = Math.lerp(y1, y2, time)
            val zMid = Math.lerp(z1, z2, time)

            obj.peek().positionMatrix.translation(xMid.toFloat(), yMid.toFloat(), zMid.toFloat())

            time += step
        } else {
            val x1 = posArray[pointer].x
            val y1 = posArray[pointer].y
            val z1 = posArray[pointer].z
            val x2 = initPos.x
            val y2 = initPos.y
            val z2 = initPos.z

            val xMid = Math.lerp(x1, x2, time)
            val yMid = Math.lerp(y1, y2, time)
            val zMid = Math.lerp(z1, z2, time)

            obj.peek().positionMatrix.translation(xMid.toFloat(), yMid.toFloat(), zMid.toFloat())

            if(time % 1.0 == 0.0) {
                pointer++
            }
            time += step
        }

    }

}
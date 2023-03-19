package com.curiouslad.thunderforge.interpolation

import net.minecraft.client.util.math.MatrixStack
import org.joml.Math
import org.joml.Vector3d

class LinearSpline(private val posArray: Array<Vector3d>, private val initPos: Vector3d) {

    var pointer = 0
    var time = 0.0
    private var xMid = 0.0
    private var yMid = 0.0
    private var zMid = 0.0

    fun transform(obj: MatrixStack, step: Double) {
        if (pointer + 1 < posArray.size) {
            val x1 = posArray[pointer].x
            val y1 = posArray[pointer].y
            val z1 = posArray[pointer].z
            val x2 = posArray[pointer + 1].x
            val y2 = posArray[pointer + 1].y
            val z2 = posArray[pointer + 1].z

            xMid = Math.lerp(x1, x2, time)
            yMid = Math.lerp(y1, y2, time)
            zMid = Math.lerp(z1, z2, time)
        }
        obj.translate(xMid.toFloat(), yMid.toFloat(), zMid.toFloat())
        if (time % 1.0 == 0.0) {
            if (pointer + 1 < posArray.size) {
                pointer++
            } else {
                pointer = 0
            }
        }
        time += step


    }

}
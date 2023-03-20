package com.curiouslad.thunderforge.interpolation

import net.minecraft.client.util.math.MatrixStack
import org.joml.Math
import org.joml.Vector3d
import kotlin.math.roundToLong

class LinearSpline(private val posArray: Array<Vector3d>) {

    private var pointer = 0
    private var time = 0.0
    private var xMid = 0.0
    private var yMid = 0.0
    private var zMid = 0.0

    var x1 = 0.0
    var y1 = 0.0
    var z1 = 0.0
    var x2 = 0.0
    var y2 = 0.0
    var z2 = 0.0


    fun transform(obj: MatrixStack) {
        val pointerCheck = pointer + 1 < posArray.size

        if (pointerCheck) {
            x1 = posArray[pointer].x
            y1 = posArray[pointer].y
            z1 = posArray[pointer].z
            x2 = posArray[pointer + 1].x
            y2 = posArray[pointer + 1].y
            z2 = posArray[pointer + 1].z

            /*println("X translated: $xMid")
            println("Y translated: $yMid")
            println("Z translated: $zMid")*/
        }

        xMid = Math.lerp(x1, x2, time*2)
        yMid = Math.lerp(y1, y2, time*2)
        zMid = Math.lerp(z1, z2, time*2)

        if ((time % 1).roundToLong() > 0.9) {
            if (pointerCheck) {
                pointer++

            } else {
                pointer = 0
            }
            time = 0.0
        }
        obj.translate(xMid.toFloat(), yMid.toFloat(), zMid.toFloat())


        time += 1 / 2e3
        /*println("Time rn: $time")
        println("Time mod 1: " + (time % 1).toString())*/
    }

}


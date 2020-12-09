package com.soakay.animateme.math

/*
 * Copyright 2020 Aali Ansari. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.PointF
import kotlin.math.acos
import kotlin.math.sqrt

/**
Name: Aali Ansari
Date: 2020/12/05
Description: a class to represent a 2D vector, used to perform angle calculations, some methods are untested
**/

class Vector(val point1: PointF, val point2: PointF) {

    val xComponent : Float = point2.x - point1.x
    val yComponent : Float = point2.y - point1.y
    val magnitude : Float =  sqrt(xComponent*xComponent + yComponent*yComponent)
    val slope : Float = yComponent/xComponent
    val yIntercept : Float = point1.y - point1.x * slope

    fun getDotProduct(v2: Vector): Float {
        return xComponent * v2.xComponent + yComponent * v2.yComponent
    }

    fun getRadians(v2: Vector) : Float {
        return acos(getDotProduct(v2) / (magnitude * v2.magnitude))
    }

    fun getAngle(v2: Vector, flip: Boolean): Float {
        var deg = Math.toDegrees(getRadians(v2).toDouble())
        if(v2.point1.y < v2.point2.y) {
             deg = 360 - deg
        }
        if(flip) {
            deg = 360 - deg
        }
        return deg.toFloat()
    }

    fun getAngle(v2: Vector): Float {
        return getAngle(v2, false)
    }

    fun calculateY(x: Float) : Float {
        return (slope * x + yIntercept)
    }

    override fun toString() : String {
        return "Origin Point: $point1\n Destination Point: $point2"
    }
}
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
import androidx.core.graphics.plus
import kotlin.math.acos
import kotlin.math.sqrt

/**
Name: Aali Ansari
Date: 2020/12/05
Description: a class to represent a 2D vector, used to perform angle calculations, some methods and overloads are untested
**/

class Vector(val point1: PointF, val point2: PointF) {

    fun getXComponent(): Float {
        return point2.x - point1.x
    }

    fun getYComponent(): Float {
        return point2.y - point1.y
    }

    fun getMagnitude(): Float {
        val x = getXComponent()
        val y = getYComponent()
        return sqrt(x*x + y*y)
    }

    fun getDotProduct(v2: Vector): Float {
        return getXComponent() * v2.getXComponent() + getYComponent() * v2.getYComponent()
    }

    fun getRadians(v2: Vector) : Float {
        return acos(getDotProduct(v2) / (getMagnitude() * v2.getMagnitude()))
    }

    fun getAngle(v2: Vector): Float {
        var deg = Math.toDegrees(getRadians(v2).toDouble())
        if(v2.point1.y < v2.point2.y) {
             deg = 360 - deg
        }
        if(getXComponent() > 0) {
            deg = 360 - deg
        }
        return deg.toFloat()
    }

    fun getSlope(): Float {
        return getYComponent()/getXComponent()
    }

    fun getYIntercept() : Float {
        return point1.y - point1.x * getSlope()
    }

    fun calculateY(x: Float) : Float {
        return (getSlope() * x + getYIntercept())
    }

    operator fun Vector.plus(other: Vector): Vector = Vector(point1 + other.point1, point2 + other.point2)
    operator fun Vector.times(other: Float) : Vector = Vector(PointF(point1.x * other, point1.y * other), PointF(point2.x * other, point2.y * other))
    operator fun Vector.div(other: Float) : Vector = Vector(PointF(point1.x / other, point1.y/other), PointF(point2.x / other, point2.y / other))

    fun normalize() : Vector {
        return this / this.getMagnitude()
    }

    override fun toString() : String {
        return "Origin Point: $point1\n Destination Point: $point2"
    }
}
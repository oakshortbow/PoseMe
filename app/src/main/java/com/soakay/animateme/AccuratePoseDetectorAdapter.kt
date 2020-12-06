package com.soakay.animateme

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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.soakay.animateme.google.GraphicOverlay
import com.soakay.animateme.google.PoseGraphic
import com.soakay.animateme.math.Vector
import java.lang.Exception
import kotlin.properties.Delegates

class AccuratePoseDetectorAdapter(val context: Context, val mode:Int, val overlay: GraphicOverlay) : OnSuccessListener<Pose>, OnFailureListener {

    val client = PoseDetection.getClient(AccuratePoseDetectorOptions.Builder()
        .setDetectorMode(mode)
        .build())

    lateinit var poses: Pose

    var leftShoulderAngle by Delegates.notNull<Float>()
    var leftElbowAngle by Delegates.notNull<Float>()
    var rightShoulderAngle by Delegates.notNull<Float>()
    var rightElbowAngle by Delegates.notNull<Float>()

    fun processBitmap(bitmap: Bitmap) : Task<Pose> {
        overlay.clear()
        val inputImage = InputImage.fromBitmap(bitmap, 0)
        overlay.setImageSourceInfo(inputImage.width, inputImage.height, false)
        return client.process(inputImage).addOnSuccessListener(this).addOnFailureListener(this)
    }


    override fun onSuccess(pose: Pose?) {
        poses = pose!!
        overlay.add(PoseGraphic(overlay, pose))
        overlay.invalidate()

        val leftShoulder =
            pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder =
            pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow =
            pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow =
            pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist =
            pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist =
            pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip =
            pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip =
            pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee =
            pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee =
            pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle =
            pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle =
            pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        val leftPinky =
            pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky =
            pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex =
            pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex =
            pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb =
            pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb =
            pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHeel =
            pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel =
            pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex =
            pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex =
            pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)


        val v1 = Vector(rightShoulder!!.position, PointF(rightElbow!!.position.x, rightShoulder.position.y))
        val v2 = Vector(rightShoulder.position, rightElbow.position)
        val v3 = Vector(rightElbow.position, rightWrist!!.position )

        val v4 = Vector(leftShoulder!!.position, PointF(leftElbow!!.position.x, leftShoulder.position.y))
        val v5 = Vector(leftShoulder.position, leftElbow.position)
        val v6 = Vector(leftElbow.position, leftWrist!!.position )

        println("ANGLES+=====")
        rightShoulderAngle = v1.getAngle(v2)
        rightElbowAngle = v2.getAngle(v3)
        println(v1.getAngle(v2))
        println(v2.getAngle(v3))

        leftShoulderAngle = v4.getAngle(v5)
        leftElbowAngle = v5.getAngle(v6)
        println(v4.getAngle(v5))
        println(v5.getAngle(v6))

    }

    override fun onFailure(p0: Exception) {
        overlay.clear()
        Toast.makeText(context, "Failed to Process Image", Toast.LENGTH_SHORT ).show()
    }


}
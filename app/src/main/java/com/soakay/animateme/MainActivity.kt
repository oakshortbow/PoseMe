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

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.soakay.animateme.math.Angles

/**
This class handles setting the image,
image rotating, and navigation the to the rendering activity
 **/
class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var poseDetector: AccuratePoseDetectorAdapter

    private val CAMERA_REQUEST = 1
    private val IMAGE_REQUEST = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.loadedImage)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 1)
        }

        poseDetector = AccuratePoseDetectorAdapter(this, PoseDetectorOptions.SINGLE_IMAGE_MODE, findViewById(R.id.graphicOverlay))
    }

    @SuppressLint("ResourceType")
    fun onButtonClick(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.animator.grow))
        when (view.id) {
            R.id.modelViewerButton -> startActivity(Intent(this, UnityRenderingActivity::class.java).apply {
                putExtra("rightShoulder", Angles.rightShoulderAngle)
                putExtra("rightElbow", Angles.rightElbowAngle)
                putExtra("leftShoulder", Angles.leftShoulderAngle)
                putExtra("leftElbow", Angles.leftElbowAngle)
                putExtra("leftShoulder", Angles.leftShoulderAngle)
                putExtra("leftElbow", Angles.leftElbowAngle)
                putExtra("rightLeg", Angles.rightLegAngle)
                putExtra("leftLeg", Angles.leftLegAngle)
                putExtra("rightKneecap", Angles.rightKneecapAngle)
                putExtra("leftKneecap", Angles.leftKneecapAngle)
            })
            R.id.cameraButton -> showCameraOptions()
            R.id.rotateButton -> rotateImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = data!!.extras!!.get("data") as Bitmap
        }
        else if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data!!.data)
        }

        //imageView.setImageBitmap(bitmap)
        poseDetector.processBitmap(bitmap)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun Bitmap.rotate(degrees: Float): Bitmap {
        return Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)
    }

    private fun showCameraOptions() {
        showListDialog("Image Provider", arrayOf("Camera", "File Explorer")) { _: DialogInterface?, selectedOption: Int ->
            when(selectedOption) {
                0 -> startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST)
                1 -> startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" }, IMAGE_REQUEST)
            }
        }
    }

    private fun rotateImage() {
        if(this::bitmap.isInitialized) {
            bitmap = bitmap.rotate(90f)
            //imageView.setImageBitmap(bitmap)
            poseDetector.processBitmap(bitmap)
        }
    }

    private fun showListDialog(title: String?, options: Array<String?>?, listener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this).setTitle(title).setItems(options, listener).create().show()
    }
}
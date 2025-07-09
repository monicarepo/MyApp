package com.ms.ecommerceapp.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ms.ecommerceapp.R
import com.ms.facedetector.FaceDetectorHelper
import java.util.concurrent.Executors

class FaceDetectionActivity: AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private lateinit var faceDetectorHelper: FaceDetectorHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        previewView = PreviewView(this)
        faceDetectorHelper = FaceDetectorHelper()
        setContentView(previewView)
        //checkCameraPermission()
        startSelectImage()

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        } else {
//            startCamera()
            startSelectImage()
        }
    }

    private fun startSelectImage() {
        checkFaceDetection()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val bitmap = imageProxy.toBitmap()
                imageProxy.close()

                val testBitmap = BitmapFactory.decodeResource(resources, R.drawable.face_detection)
                faceDetectorHelper.detectFace(
                    testBitmap,
                    onSuccess = { faces -> println("✅ Faces found: ${faces.size}") },
                    onFailure = { e -> println("❌ Error: ${e.message}") }
                )

                runOnUiThread {
                    faceDetectorHelper.detectFace(
                        testBitmap,
                        onSuccess = { faces ->
                            println("Detected faces: ${faces.size}")
                            // Handle face list
                        },
                        onFailure = { error ->
                            error.printStackTrace()
                        }
                    )
                }
            }

            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, preview, imageAnalysis)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun checkFaceDetection() {

//        val testBitmap = BitmapFactory.decodeResource(resources, R.drawable.face_detection)

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        // Step 1: Decode dimensions only
        BitmapFactory.decodeResource(resources, R.drawable.face_image, options)

        // Step 2: Calculate appropriate sample size
        val reqWidth = 1024
        val reqHeight = 1024
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Step 3: Decode actual bitmap with downscaling
        options.inJustDecodeBounds = false
        val testBitmap = BitmapFactory.decodeResource(resources, R.drawable.face_image, options)

        faceDetectorHelper.detectFace(
            testBitmap,
            onSuccess = { faces -> println("✅ Faces found: ${faces.size}") },
            onFailure = { e -> println("❌ Error: ${e.message}") }
        )

        faceDetectorHelper.detectFace(
            testBitmap,
            onSuccess = { faces ->
                println("Detected faces: ${faces.size}")
                // Handle face list
            },
            onFailure = { error ->
                error.printStackTrace()
            }
        )
    }


    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight &&
                (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }



}
package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.SurfaceView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CapturePhotoActivity : AppCompatActivity() {
    private lateinit var camera: Camera
    private lateinit var cameraPreview: CameraPreview
    private val REQUEST_CAMERA_PERMISSION = 2
    private lateinit var listView: ListView
    private lateinit var adapter: PhotoAdapter
    private lateinit var photos: MutableList<String>
    private lateinit var orientationEventListener: OrientationEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_photo)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            setupCamera()
        }

        listView = findViewById(R.id.listViewPhotos)
        photos = loadPhotos()
        adapter = PhotoAdapter(this, photos)
        listView.adapter = adapter

        val captureButton: Button = findViewById(R.id.buttonCapture)
        captureButton.setOnClickListener {
            camera.takePicture(null, null, Camera.PictureCallback { data, _ ->
                val photoPath = savePhoto(data)
                if (photoPath != null) {
                    photos.add(photoPath)
                    adapter.notifyDataSetChanged()
                }
                cameraPreview.startPreview()
            })
        }

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }

        orientationEventListener = object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) return
                val rotation = when (orientation) {
                    in 45..134 -> Surface.ROTATION_270
                    in 135..224 -> Surface.ROTATION_180
                    in 225..314 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }
                adjustCameraOrientation(rotation)
            }
        }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        }
    }

    private fun adjustCameraOrientation(rotation: Int) {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info)
        val degrees = when (rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
        val result: Int = if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            (info.orientation + degrees) % 360
        } else {
            (info.orientation - degrees + 360) % 360
        }
        camera.setDisplayOrientation(result)
    }

    private fun setupCamera() {
        camera = Camera.open()
        cameraPreview = CameraPreview(this, camera)
        val preview: SurfaceView = findViewById(R.id.camera_preview)
        preview.holder.addCallback(cameraPreview)
    }

    private fun savePhoto(data: ByteArray): String? {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val filename = "IMG_${sdf.format(Date())}.jpg"
        file = File(file, filename)

        return try {
            val stream: FileOutputStream = FileOutputStream(file)
            stream.write(data)
            stream.flush()
            stream.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadPhotos(): MutableList<String> {
        val wrapper = ContextWrapper(applicationContext)
        val directory = wrapper.getDir("images", Context.MODE_PRIVATE)
        return directory.listFiles()?.map { it.absolutePath }?.toMutableList() ?: mutableListOf()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                setupCamera()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
        orientationEventListener.disable()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupCamera()
        }
        orientationEventListener.enable()
    }

    private fun releaseCamera() {
        if (::camera.isInitialized) {
            camera.release()
        }
    }
}

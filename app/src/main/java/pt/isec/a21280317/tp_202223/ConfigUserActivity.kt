package pt.isec.a21280317.tp_202223

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigUserBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigUserLandscapeBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityMainBinding
import pt.isec.a21280317.tp_202223.databinding.ActivityMainLandscapeBinding
import java.io.File

class ConfigUserActivity : AppCompatActivity(){

    companion object {
        private const val TAG = "ConfigImageActivity"
        private const val ACTIVITY_REQUEST_CODE_GALLERY = 10
        private const val ACTIVITY_REQUEST_CODE_CAMERA  = 20
        private const val PERMISSIONS_REQUEST_CODE = 1

        private const val GALLERY = 1
        private const val CAMERA  = 2
        private const val MODE_KEY = "mode"
    }

    private var imagePath : String? = null
    private var mode = CAMERA
    private var permissionsGranted = false

    private lateinit var binding: ActivityConfigUserBinding
    private lateinit var bindingLandscapeBinding: ActivityConfigUserLandscapeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bindingLandscapeBinding = ActivityConfigUserLandscapeBinding.inflate(layoutInflater)
            setContentView(bindingLandscapeBinding.root)

            bindingLandscapeBinding.chooseImageButton.setOnClickListener{
                chooseImage()
            }

            bindingLandscapeBinding.takePicButton.setOnClickListener{
                takePhoto()
            }

        } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = ActivityConfigUserBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.chooseImageButton.setOnClickListener{
                chooseImage()
            }

            binding.takePicButton.setOnClickListener{
                takePhoto()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        verifyPermissions()
        updatePreview()
    }

    fun updatePreview() {
        if (imagePath != null){
            setPic(binding.avaPreview, imagePath!!)
            binding.avaPreview.setBackgroundColor(Color.rgb(142,175,157))
        }
        else
            binding.avaPreview.background = ResourcesCompat.getDrawable(resources,
                R.drawable.user, //android.R.drawable.ic_menu_report_image,
                null)
    }

    var startActivityForContentResult = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        Log.i(TAG, "startActivityForContentResult: ")
        imagePath = uri?.let { createFileFromUri(this, it) }
        updatePreview()
    }
    fun chooseImage() {
        Log.i(TAG, "chooseImage_v3: ")
        startActivityForContentResult.launch("image/*")
        updatePreview()
    }

    var startActivityForTakePhotoResult = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        Log.i(TAG, "startActivityForTakePhotoResult: $success")
        if (!success)
            imagePath = null
        updatePreview()
    }

    fun takePhoto() {
        imagePath = getTempFilename(this)
        Log.i(TAG, "takePhoto: $imagePath")
        startActivityForTakePhotoResult.launch(
            FileProvider.getUriForFile( this,
                "pt.isec.a21280317.tp_202223.android.fileprovider", File(imagePath)
            ))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
        permissionsGranted = grantResults.values.any { it }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionsGranted = isGranted
    }

    fun verifyPermissions() {
        Log.i(TAG, "verifyPermissions_v3: ")
        if (mode == CAMERA) {
            permissionsGranted = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            if (!permissionsGranted)
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            return
        }
        //mode == GALLERY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsGranted = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED

            if (!permissionsGranted)
                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            return
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsGranted = false
            requestPermissionsLauncher.launch(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        } else
            permissionsGranted = true
    }
}

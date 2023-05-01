package com.example.imagesaver

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        // Specify the albumId(s) and number of photos to save
        // Get the album ID entered by the user
        val albumId = 3 // Replace with the album ID entered by the user

// Specify the number of photos to save
        val numPhotos = 2 // Replace with the desired number of photos to save per album

        dataViewModel.getData()

        dataViewModel.apiResponse.observe(this) { responseList ->
            var photosSaved = 0 // Keep track of the number of photos saved so far
            for (response in responseList) {
                if (response.albumId <= albumId && photosSaved <= numPhotos) {
                    val imageUrl = response.url
                    lifecycleScope.launch {
                        val imageBitmap = withContext(Dispatchers.IO) {
                            URL(imageUrl).openStream().use { inputStream ->
                                BitmapFactory.decodeStream(inputStream)
                            }
                        }
                        val directory = getExternalFilesDir(null)
                        val fileName = "image_${response.albumId}_${response.id}.jpg" // Generate a unique file name for each image
                        val file = File(directory, fileName)
                        val fileOutputStream = FileOutputStream(file)
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                        withContext(Dispatchers.IO) {
                            fileOutputStream.flush()
                            fileOutputStream.close()
                        }
                        photosSaved++
                    }
                }
            }
        }

    }
}

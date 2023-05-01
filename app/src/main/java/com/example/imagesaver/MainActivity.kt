package com.example.imagesaver

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        dataViewModel.getData()

        dataViewModel.apiResponse.observe(this) { responseList ->
            for (response in responseList) {
                val imageUrl = response.url // Replace "imageUrl" with the name of the field in your response object that contains the image URL
                val imageBitmap = URL(imageUrl).openStream().use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
                val fileOutputStream = FileOutputStream("path/to/image.jpg") // Replace "path/to/image.jpg" with the path where you want to save the image
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            }
        }
    }
}

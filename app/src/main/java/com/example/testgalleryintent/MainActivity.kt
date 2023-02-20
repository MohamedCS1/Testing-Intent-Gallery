package com.example.testgalleryintent
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


const val GALLERY_REQUEST_CODE = 1234

class MainActivity : AppCompatActivity() {

    lateinit var buttonOpenGallery:TextView
    lateinit var mainImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOpenGallery = findViewById(R.id.buttonOpenGallery)

        mainImageView = findViewById(R.id.mainImageView)

        buttonOpenGallery.setOnClickListener {
            pickFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){

                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        Glide.with(this)
                            .load(uri)
                            .into(mainImageView)
                    }
                }
            }
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
}
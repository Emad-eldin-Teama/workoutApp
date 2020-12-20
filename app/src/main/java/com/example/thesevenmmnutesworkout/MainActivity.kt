package com.example.thesevenmmnutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun startButton(view:View){
      Toast.makeText(this,"here we start the exercise ",Toast.LENGTH_LONG).show()
    }
    }

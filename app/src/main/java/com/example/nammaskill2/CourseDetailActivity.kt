package com.example.nammaskill2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class CourseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        val btnApply = findViewById<Button>(R.id.btnApply)
        btnApply.setOnClickListener {
            startActivity(Intent(this, ApplyActivity::class.java))
        }

        val btnInterest = findViewById<Button>(R.id.btnInterest)
        btnInterest.setOnClickListener {
            Toast.makeText(this,
                "Thank you! Our trainer will call you within 24 hours!",
                Toast.LENGTH_LONG).show()
        }
    }
}
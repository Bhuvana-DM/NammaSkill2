package com.example.nammaskill2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn1 = findViewById<Button>(R.id.btnDetail1)
        val btn2 = findViewById<Button>(R.id.btnDetail2)
        val btn3 = findViewById<Button>(R.id.btnDetail3)
        val btnMap = findViewById<Button>(R.id.btnMap)

        // Load data from Firebase
        val database = FirebaseDatabase.getInstance()
        val coursesRef = database.getReference("courses")

        coursesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (courseSnapshot in snapshot.children) {
                    val title = courseSnapshot.child("title").getValue(String::class.java)
                    val duration = courseSnapshot.child("duration").getValue(String::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        val chipShort = findViewById<com.google.android.material.chip.Chip>(R.id.chipShort)
        val chipLong = findViewById<com.google.android.material.chip.Chip>(R.id.chipLong)

        chipShort.setOnClickListener {
            Toast.makeText(this,
                "Showing Short Term courses (1-3 Months)",
                Toast.LENGTH_SHORT).show()
        }

        chipLong.setOnClickListener {
            Toast.makeText(this,
                "Showing Long Term courses (4-6 Months)",
                Toast.LENGTH_SHORT).show()
        }

        btn1.setOnClickListener {
            startActivity(Intent(this, CourseDetailActivity::class.java))
        }
        btn2.setOnClickListener {
            startActivity(Intent(this, CourseDetailActivity::class.java))
        }
        btn3.setOnClickListener {
            startActivity(Intent(this, CourseDetailActivity::class.java))
        }
        btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        val btnStories = findViewById<Button>(R.id.btnStories)
        btnStories.setOnClickListener {
            startActivity(Intent(this, SuccessStoriesActivity::class.java))
        }
    }
}
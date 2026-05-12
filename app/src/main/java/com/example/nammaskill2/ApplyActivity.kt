package com.example.nammaskill2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etEducation = findViewById<EditText>(R.id.etEducation)
        val etVillage = findViewById<EditText>(R.id.etVillage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val age = etAge.text.toString()
            val education = etEducation.text.toString()
            val village = etVillage.text.toString()

            if (name.isEmpty() || phone.isEmpty() ||
                age.isEmpty() || education.isEmpty() ||
                village.isEmpty()) {
                Toast.makeText(this,
                    "Please fill all fields!",
                    Toast.LENGTH_SHORT).show()
            } else {
                saveToFirebase(name, phone, age, education, village)
                generateSummary(name, phone, age, education, village)
            }
        }
    }

    private fun saveToFirebase(
        name: String, phone: String,
        age: String, education: String,
        village: String) {

        val database = FirebaseDatabase.getInstance("https://nammaskill-9bfbb-default-rtdb.asia-southeast1.firebasedatabase.app")
        val applicationsRef = database.getReference("applications")

        val applicationData = mapOf(
            "name" to name,
            "phone" to phone,
            "age" to age,
            "education" to education,
            "village" to village,
            "timestamp" to System.currentTimeMillis()
        )

        applicationsRef.push().setValue(applicationData)
            .addOnSuccessListener {
                Toast.makeText(this,
                    "Application saved successfully!",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,
                    "Failed to save application!",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun generateSummary(
        name: String, phone: String,
        age: String, education: String,
        village: String) {

        val apiKey = "AIzaSyAuRPQRLqAtEch9Kgk9RYbJXgkcXtwuQZo"
        val model = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )

        val prompt = """
            Generate a professional candidate summary for skill training:
            Name: $name
            Age: $age
            Phone: $phone
            Education: $education
            Village: $village
            Keep it short and professional in 3-4 lines.
        """.trimIndent()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = model.generateContent(prompt)
                val summary = response.text ?: "Application submitted successfully!"
                withContext(Dispatchers.Main) {
                    showSummaryDialog(summary)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ApplyActivity,
                        "Application Submitted Successfully! ✅",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showSummaryDialog(summary: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("✅ Application Submitted!")
            .setMessage(summary)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }
}
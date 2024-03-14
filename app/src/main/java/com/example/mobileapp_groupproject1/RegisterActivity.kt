package com.example.mobileapp_groupproject1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern



class RegisterActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var etMobileNo: TextInputEditText
    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var userReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        userReference = firebaseDatabase.reference.child("users")

        editTextEmail = findViewById(R.id.register_email)
        editTextPassword = findViewById(R.id.password)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etMobileNo = findViewById(R.id.etMobileNo)
        buttonReg = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val mobileNo = etMobileNo.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(mobileNo)) {
                Toast.makeText(this@RegisterActivity, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                Toast.makeText(this@RegisterActivity, "Enter valid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                Toast.makeText(this@RegisterActivity, "Password must have at least 1 Character, 1 Digit & 1 Special Symbol & 8 Characters Long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgressDialog()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@RegisterActivity) { task ->
                    progressBar.visibility = View.GONE
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        addUserToDatabase(email, password, firstName, lastName, mobileNo)
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun addUserToDatabase(email: String, password: String, firstName: String, lastName: String, mobileNo: String) {
        val currentUser = mAuth.currentUser
        val userId = currentUser?.uid ?: ""
        val user = User(email, password, firstName, lastName, mobileNo)

        userReference.child(userId).setValue(user)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Failed to add user to database", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    companion object {
        private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$")
        private val EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    }
}
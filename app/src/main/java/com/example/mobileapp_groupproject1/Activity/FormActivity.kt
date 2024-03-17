package com.example.mobileapp_groupproject1.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.*
import com.example.mobileapp_groupproject1.Entity.Appointment
import com.example.mobileapp_groupproject1.R

class FormActivity : AppCompatActivity() {
    //I am declaring the private variables to store each data input from form
    private lateinit var companyNameEditText: EditText;
    private lateinit var recruiterFirstNameEditText: EditText;
    private lateinit var recruiterLastNameEditText: EditText;
    private lateinit var emailEditText: EditText;
    private lateinit var phoneNumberEditText: EditText;
    private lateinit var dateTextView: TextView;
    private lateinit var timeTextView: TextView;
    private lateinit var submitButton: Button;
    private val database = FirebaseDatabase.getInstance();
    private val appointmentsRef = database.getReference("appointments");
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //referring the activity_form xml
        setContentView(R.layout.activity_form);
        //locating each field by using findViewById method.
        companyNameEditText = findViewById(R.id.editCompanyName);
        recruiterFirstNameEditText = findViewById(R.id.editRecruiterFirstName);
        recruiterLastNameEditText = findViewById(R.id.editRecruiterLastName);
        emailEditText = findViewById(R.id.editEmail);
        phoneNumberEditText = findViewById(R.id.editPhoneNumber);
        dateTextView = findViewById(R.id.textDateLabel);
        timeTextView = findViewById(R.id.textTimeLabel);
        submitButton = findViewById(R.id.buttonSubmit);
        dateTextView.setOnClickListener {//on click listener for data text view
            showDatePicker();       //calling showDatePicker Method
        }

        timeTextView.setOnClickListener { //on click listener for time text view
            showTimePicker();    //calling showTimePicker method
        }

        submitButton.setOnClickListener {  //onclick listener for submit button
            if (validateForm()) {  //if form is validate(method to validate the form)
                scheduleAppointment();  //if form validated successfully calling scheduleAppointment method
            }
        }
        val cancelButton = findViewById<Button>(R.id.buttonCancel); //on click listener for cancel button
        cancelButton.setOnClickListener {
            resetForm();  //will reset the form by calling that method, method functionality written below
            navigateBackToDetailsActivityPage();  //created separate method to navigate to the different page.
        }
    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance();
        val year = calendar.get(Calendar.YEAR);
        val month = calendar.get(Calendar.MONTH);
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = formatDate(selectedYear, selectedMonth, selectedDay)
                dateTextView.text = selectedDate
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show();
    }
    private fun showTimePicker() {
        val calendar = Calendar.getInstance();
        val hour = calendar.get(Calendar.HOUR_OF_DAY);
        val minute = calendar.get(Calendar.MINUTE);

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = formatTime(selectedHour, selectedMinute)
                timeTextView.text = selectedTime
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show();
    }
    private fun validateForm(): Boolean {  //validating each field in the form whether user provided or not, if not will through the information toast message
        val companyName = companyNameEditText.text.toString();
        val recruiterFirstName = recruiterFirstNameEditText.text.toString();
        val recruiterLastName = recruiterLastNameEditText.text.toString();
        val email = emailEditText.text.toString();
        val phoneNumber = phoneNumberEditText.text.toString();

        if (companyName.isEmpty() || recruiterFirstName.isEmpty() || recruiterLastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show(); //Toast message to display on front end.
            return false;
        }
        return true;
    }

    private fun scheduleAppointment() { //scheduleappointment method that saves data to the firebase if all fields are provided.
        val companyName = companyNameEditText.text.toString();
        val recruiterFirstName = recruiterFirstNameEditText.text.toString();
        val recruiterLastName = recruiterLastNameEditText.text.toString();
        val email = emailEditText.text.toString();
        val phoneNumber = phoneNumberEditText.text.toString();
        val selectedDate = dateTextView.text.toString();
        val selectedTime = timeTextView.text.toString();

        val appointmentId = appointmentsRef.push().key; //auto generated key pushing to the firebase
        val appointment = Appointment(
            appointmentId,
            companyName,
            recruiterFirstName,
            recruiterLastName,
            email,
            phoneNumber,
            selectedDate,
            selectedTime
        )
        appointmentsRef.child(appointmentId ?: "").setValue(appointment) //pushing all values to the one auto generated reference number in above step
            .addOnSuccessListener {

                resetForm();
                val intent = Intent(this, HomeActivity::class.java);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Interview Appointment scheduled successfully with the candidate", Toast.LENGTH_SHORT).show() //toast message for successful transaction
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to schedule the appointment: ${exception.message}", Toast.LENGTH_SHORT).show() //toast message for failure transaction
            }
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.time);
    }

    private fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute);
    }
    private fun resetForm() { //resetting to empty values for all fields in the form
        companyNameEditText.text.clear()
        recruiterFirstNameEditText.text.clear()
        recruiterLastNameEditText.text.clear()
        emailEditText.text.clear()
        phoneNumberEditText.text.clear()
        dateTextView.text = "Click here To Select the date for appointment"
        timeTextView.text = "Click Here to select Time for an interview"
    }
    private fun navigateBackToDetailsActivityPage() { //method to navigate back to detail activity page when user clicks on cancel button will be called from cancel button onclick listener function
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
        finish()
    }

}
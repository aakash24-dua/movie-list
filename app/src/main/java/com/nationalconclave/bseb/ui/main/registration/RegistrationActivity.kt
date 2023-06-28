package com.nationalconclave.bseb.ui.main.registration

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.databinding.ActivityRegistrationBinding
import com.nationalconclave.bseb.ui.main.otp.OtpActivity
import com.nationalconclave.bseb.utils.isValidEmail
import com.nationalconclave.bseb.utils.isValidPhoneNumber
import com.nationalconclave.bseb.utils.showToast
import java.util.*


class RegistrationActivity : AppCompatActivity() {


    val selectedItems : BooleanArray by lazy {
        BooleanArray(dataArray.size)
    }
    var dataList: ArrayList<Int> = ArrayList()
    var dataArray = arrayOf("Nalanda","Rajgir","Bodhgaya","Vaishali")

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
    }


    lateinit var binding : ActivityRegistrationBinding


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            // Handle the back button press event
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Registration"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.arrivalDate.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                R.style.DatePickerDialogTheme,
                { view, year, monthh, dayy ->
                    // on below line we are setting
                    // date to our text view.
                    var monthOfYear = (monthh.plus(1)).toString()
                    if (monthOfYear.length == 1){
                        monthOfYear = "0$monthOfYear"
                    }
                    var dayOfMonth = dayy.toString()
                    if (dayOfMonth.length == 1){
                        dayOfMonth = "0$dayOfMonth"
                    }
                    val dep =year.toString() + "-" + monthOfYear + "-" + dayOfMonth
                    binding.arrivalDate.setText(dep)


                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )

            val minDateCalendar = Calendar.getInstance()
            minDateCalendar[year, month] =
                day // Set the minimum date to the current date

            datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

            val maxDateCalendar = Calendar.getInstance()
            maxDateCalendar[year, month] =
                day + 10 // Set the maximum date to 7 days from the current date

            datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis
            datePickerDialog.show()
        }


        binding.departureDate.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                R.style.DatePickerDialogTheme,
                { view, year, monthh, dayy ->
                    // on below line we are setting
                    // date to our text view.
                    var monthOfYear = (monthh.plus(1)).toString()
                    if (monthOfYear.length == 1){
                        monthOfYear = "0$monthOfYear"
                    }
                    var dayOfMonth = dayy.toString()
                    if (dayOfMonth.length == 1){
                        dayOfMonth = "0$dayOfMonth"
                    }
                    val dep =year.toString() + "-" + monthOfYear + "-" + dayOfMonth
                    binding.departureDate.setText(dep)

                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )

            // Set the minimum date (optional)
            // Set the minimum date (optional)
            val minDateCalendar = Calendar.getInstance()
            minDateCalendar[year, month] =
                day // Set the minimum date to the current date

            datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

// Set the maximum date (optional)

// Set the maximum date (optional)
            val maxDateCalendar = Calendar.getInstance()
            maxDateCalendar[year, month] =
                day + 10 // Set the maximum date to 7 days from the current date

            datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        //handleSpinner()
        handleSightSeeingText()


        binding.buttonRegister.setOnClickListener {
            validateAll()
        }

        viewModel.publishObject.subscribe { response->
            response.id?.let { userId ->
                response?.phoneNo?.let { phoneNo ->
                    showToast("Otp has been sent to your mobile number")
                    startActivity(OtpActivity.getIntent(this@RegistrationActivity,userId,phoneNo))
                }
            }

        }

        viewModel.showToast.subscribe{ errorString->
            Toast.makeText(this,errorString,Toast.LENGTH_LONG).show()

        }



    }

    private fun validateAll() {
        val firstName = binding.name.text.toString()
        val lastName = binding.lastName.text.toString()
        val arrival = binding.arrivalDate.text.toString()
        val departure = binding.departureDate.text.toString()
        val mobile = binding.editTextMobile.text.toString()
        val email = binding.email.text.toString()
        val org = binding.orgName.text.toString()
        val designation = binding.designation.text.toString()
        val tvSiteSeeing = binding.tvSiteSeeing.text.toString()
        val arrivalFlightNo = binding.arrivalFlightNo.text.toString()
        val departureFlightNo = binding.departureFlightNo.text.toString()
        //val password = binding.editTextPassword.text.toString()
        //val confirmPassword = binding.editTextPasswordConfirm.text.toString()
        if (firstName.isBlank()
            || arrival.isBlank()
            || departure.isBlank()
            || mobile.isBlank()
            || org.isBlank()
            || designation.isBlank()
            || arrivalFlightNo.isBlank()
            || departureFlightNo.isBlank()
           // || password.isBlank()
           // || confirmPassword.isBlank()
        ){
            showToast("Mandatory Fields can't be empty")
            return
        }

        if (binding.editTextMobile.text.toString().isValidPhoneNumber().not()){
            showToast("Enter Valid Phone Number")
            return
        }


        if (email.isNotEmpty() && email.isValidEmail().not()){
            showToast("Email is invalid")
            return
        }




//        if (password.contentEquals(confirmPassword).not()){
//            showToast("Passwords do not match")
//            return
//        }
       // val list = tvSiteSeeing.split(",")
        val list = null
        val request = RegistrationRequest(firstName,lastName,org,designation,email,mobile,arrival,departure,arrivalFlightNo,departureFlightNo,false, list)
        viewModel.registerUser(request)
       // startActivity(SightSeeingRegActivity.getIntent(this,request))

    }

    private fun handleSightSeeingText() {
        binding.tvSiteSeeing.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this,R.style.AlertDialogTheme)

            // set title

            // set title
            builder.setTitle("Select Cities for Sight Seeing")

            // set dialog non cancelable

            // set dialog non cancelable
            builder.setCancelable(false)

            builder.setMultiChoiceItems(dataArray, selectedItems,
                OnMultiChoiceClickListener { dialogInterface, i, b ->
                    // check condition
                    if (b) {
                        // when checkbox selected
                        // Add position  in lang list
                        dataList.add(i)
                        // Sort array list
                        Collections.sort(dataList)
                    } else {
                        // when checkbox unselected
                        // Remove position from langList
                        dataList.remove(Integer.valueOf(i))
                    }
                })

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialogInterface, i -> // Initialize string builder
                    val stringBuilder = StringBuilder()
                    // use for loop
                    for (j in 0 until dataList.size) {
                        // concat array value
                        stringBuilder.append(dataArray.get(dataList.get(j)))
                        // check condition
                        if (j != dataList.size - 1) {
                            // When j value  not equal
                            // to lang list size - 1
                            // add comma
                            stringBuilder.append(", ")
                        }
                    }
                    // set text on textView
                    binding.tvSiteSeeing.setText(stringBuilder.toString())
                })

            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> // dismiss dialog
                    dialogInterface.dismiss()
                })
            builder.setNeutralButton("Clear All",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // use for loop
                    for (j in 0 until selectedItems.size) {
                        // remove all selection
                        selectedItems[j] = false
                        // clear language list
                        dataList.clear()
                        // clear text view value
                        binding.tvSiteSeeing.setText(getString(R.string.select_items))
                    }
                })
            // show dialog
            // show dialog
            builder.show()
        }
    }

//    private fun handleSpinner() {
//        val spinner = binding.spinnerSightSeeing
//        val dataList = listOf<String>("Nalanda","Rajgir","Bodhgaya","Vaishali")
//        val adapter = MultiSelectionAdapter(this, android.R.layout.simple_spinner_item, dataList)
//        spinner.adapter = adapter
//        spinner.prompt = "Select items" // Optional: Set a prompt for the spinner
//
//        // spinner.setSelection(adapter.getSelectedItems()) // Restore selected items if needed
//
//
//        spinner.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val adapter: MultiSelectionAdapter = parent.adapter as MultiSelectionAdapter
//                adapter.setSelectedItem(position, !adapter.getSelectedItems().get(position))
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // Handle the case where no item is selected
//            }
//        }
//    }
}
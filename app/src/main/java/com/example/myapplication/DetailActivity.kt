package com.example.myapplication

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.app.AppController
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    val calendar = Calendar.getInstance()
    lateinit var isFrom: String
    var userEntity: UserEntity? = null
    private var userDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        userDao = AppController.getAppDatabaseInstance()?.userDao()
        val intent = intent
        isFrom = intent.getStringExtra("isFrom")

        btnSubmit.setOnClickListener(this)
        edTimeAndDate.setOnClickListener(this)

        if (isFrom == "edit") {
            btnSubmit.text = "Update"
            userEntity = intent.getSerializableExtra("entity") as UserEntity
            edTitle.setText(userEntity?.title)
            edDescription.setText(userEntity?.description)
            edTimeAndDate.setText(userEntity?.dateAndTime)
        } else if(isFrom == "fabButton") {
            btnSubmit.text = "Submit"
        }
    }

    private fun insert() {
        userDao?.insert(
            UserEntity(
                edTitle.text.toString(),
                edDescription.text.toString(),
                edTimeAndDate.text.toString()
            )
        )
        edTitle.text = null
        edDescription.text = null
        edTimeAndDate.text = null
        onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSubmit -> {
                if (isFrom == "fabButton") {
                    insert()
                } else if (isFrom == "edit") {
                    updateData()
                }
            }
            R.id.edTimeAndDate -> {
                val date =
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateLabel()
                    }

                val datePickerDialog = DatePickerDialog(
                    this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy HH:mm" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edTimeAndDate.setText(sdf.format(calendar.time))
    }

    private fun updateData() {
        val user = userEntity
        user?.title = edTitle.text.toString()
        user?.description = edDescription.text.toString()
        user?.dateAndTime = edTimeAndDate.text.toString()
        user?.let { userDao?.update(it) }
        edTitle.text = null
        edDescription.text = null
        edTimeAndDate.text = null
        onBackPressed()
    }
}
package com.example.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.myapplication.app.AppController
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.custom_dialog.view.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvChangeTextSize.setOnClickListener {
            alertDialog()
        }

    }

    private fun alertDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Change text size")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.btnChange.setOnClickListener {
            val text = mDialogView.edEnterTextSize.text.toString()
            val textSize = text.toInt()
            AppController.sharedPrefs?.textSize = textSize
            mAlertDialog.dismiss()
        }
    }
}
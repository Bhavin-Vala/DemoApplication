package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.adapter.MainAdapter
import com.example.myapplication.app.AppController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mainAdapter by lazy { MainAdapter(::handleDelete, ::handleEdit) }
    private var userDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDao = AppController.getAppDatabaseInstance()?.userDao()
        initRecyclerView()
        btnFab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnFab -> {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("isFrom", "fabButton")
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        rvDetails.adapter = mainAdapter
        userDao?.getAllData()?.observe(this, ::handleDetails)
    }

    private fun handleDetails(list: List<UserEntity>?) {
        mainAdapter.addData(list = list as ArrayList)
    }

    private fun handleEdit(userEntity: UserEntity) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("isFrom", "edit")
        intent.putExtra("entity", userEntity)
        startActivity(intent)
    }

    private fun handleDelete(userEntity: UserEntity, position: Int) {
        userDao?.delete(userEntity)
        mainAdapter.removeAt(position)
    }
}
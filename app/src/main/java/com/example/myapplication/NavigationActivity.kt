package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.adapter.MainAdapter
import com.example.myapplication.app.AppController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*
import kotlin.math.roundToInt


class NavigationActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val mainAdapter by lazy { MainAdapter(::handleDelete, ::handleEdit) }
    private var userDao: UserDao? = null
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_settings
            ), drawerLayout
        )

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(this)

        userDao = AppController.getAppDatabaseInstance()?.userDao()
        initRecyclerView()
        btnFab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFab -> {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("isFrom", "fabButton")
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun getDPsFromPixels(pixels: Int): Int {
        val resources = applicationContext.resources
        return (pixels / (resources.displayMetrics.densityDpi / 160f)).roundToInt()
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
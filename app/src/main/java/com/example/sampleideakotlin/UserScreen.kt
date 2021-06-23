package com.example.sampleideakotlin

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.TextureView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.sampleideakotlin.fragment.BottomSheet
import com.example.sampleideakotlin.fragment.BottomSheetFragment
import com.example.sampleideakotlin.fragment.BroadcastFragment
import com.example.sampleideakotlin.fragment.ServiceFragment
import com.google.android.material.navigation.NavigationView

class UserScreen : AppCompatActivity() {
   // private lateinit var navContr
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_screen)
        val toolBar=findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        val navigationView=findViewById<NavigationView>(R.id.navView)
        drawerLayout=findViewById(R.id.drawerLayout)
         toggle=ActionBarDrawerToggle(
             this,
             drawerLayout,
             toolBar,
             R.string.open_drawer,
             R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        setUpDrawerContent(navigationView)

        //to set a default fragment in drawer menu to screen
        navigationView.menu.getItem(0).isChecked = true
        navigationView.menu.performIdentifierAction(R.id.first, 0);

        val sharedPreferences=applicationContext.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)



        val header: View =navigationView.getHeaderView(0)
        val txtHeadUserName=header.findViewById<TextView>(R.id.txtHeaderUserName)
        val txtHeadUserEmail=header.findViewById<TextView>(R.id.txtHeaderUserEmail)

        if (sharedPreferences!=null){
            val savedUserName: String? =sharedPreferences.getString("userName","")
            val savedUserPassword:String?=sharedPreferences.getString("userPassword","")
            val savedUserEmail:String?=sharedPreferences.getString("userEmail","")
            val savedUserSex:String?=sharedPreferences.getString("userSex","")
            RegisterActivity.credentials= Credentials(savedUserName,savedUserPassword,savedUserEmail,savedUserSex)
                try {
                    txtHeadUserName.text= "UserName $savedUserName"
                    txtHeadUserEmail.text="Email Id $savedUserName"
                }catch (e:Exception){
                    e.stackTrace
                }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { item ->
            selectDrawerItem(item)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null
        fragmentClass = when (menuItem.itemId) {
            R.id.first -> BottomSheetFragment::class.java
            R.id.second -> BroadcastFragment::class.java
            R.id.third -> ServiceFragment::class.java
            else ->BottomSheetFragment::class.java
        }
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commit()
        menuItem.isChecked = true
//
        when {
            menuItem.groupId==R.id.mainMenu -> {
                title = menuItem.title
            }
            menuItem.itemId==R.id.logOut -> {
                logOut()
            }
            else -> {
                Toast.makeText(applicationContext,"You Clicked ${menuItem.title}",Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawers()

    }

    private fun logOut(){
        val sharedPreferences=applicationContext.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        val builder=AlertDialog.Builder(this)
        builder.setMessage("Are You Want Logout?")

        builder.setPositiveButton("Yes"){ _, _ ->
            editor.clear()
            editor.apply()
            finish()
        }
        builder.setNegativeButton("No"){ di, _ ->
            di.cancel()
        }

        val alertDialog=builder.create()
        alertDialog.show()

    }



}
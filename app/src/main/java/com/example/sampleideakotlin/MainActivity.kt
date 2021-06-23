package com.example.sampleideakotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.sampleideakotlin.fragment.BottomSheet

const  val sharedPrefFile="kotlinSharedPref"
class MainActivity : AppCompatActivity() {

    var counter:Int=5
    var isValid:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtUserName=findViewById<EditText>(R.id.edtUserName)
        val edtUserPassword=findViewById<EditText>(R.id.edtUserPassword)
        val btnLogin =findViewById<Button>(R.id.btnLogin)
        val cbRememberMe =findViewById<CheckBox>(R.id.cbRememberMe)

        val sharedPreferences=applicationContext.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()

        if (sharedPreferences!=null){

            val savedUserName: String? =sharedPreferences.getString("userName","")
            val savedUserPassword:String?=sharedPreferences.getString("userPassword","")
            val savedUserEmail:String?=sharedPreferences.getString("userEmail","")
            val savedUserSex:String?=sharedPreferences.getString("userSex","")
            RegisterActivity.credentials= Credentials(savedUserName,savedUserPassword,savedUserEmail,savedUserSex)

            if (sharedPreferences.getBoolean("rememberMe",false)){
               edtUserName.setText(savedUserName)
               edtUserPassword.setText(savedUserPassword)
               cbRememberMe.isChecked=true
            }
        }

        cbRememberMe.setOnClickListener(View.OnClickListener {
            editor.putBoolean("rememberMe",cbRememberMe.isChecked)
            editor.putBoolean("isUserLogin",true)
            editor.apply()
        })

        btnLogin.setOnClickListener(View.OnClickListener {
            val inputName:String=edtUserName.text.toString()
            val  inputPassword:String=edtUserPassword.text.toString()

            if (inputName.isEmpty()|| inputPassword.isEmpty()){
                Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show()
            }else{
                isValid=validate(inputName,inputPassword)

                if (!isValid){
                    counter--
                    Toast.makeText(this,"Incorrect Credentials attempt remaining $counter",Toast.LENGTH_SHORT).show()

                    if (counter==0){
                        btnLogin.isEnabled=false
                    }
                }else{
                    Toast.makeText(this,"Login Successful !",Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this,UserScreen::class.java))
                    finish()

                }
            }
        })


    }

    fun navigateRegisterPage(view: View) {
        if (view.id==R.id.txtClickToNxtAct){
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun validate(userName:String,userPassword:String):Boolean{

            if (userName==RegisterActivity.credentials.userName && userPassword==RegisterActivity.credentials.userPassword){
                    return true
            }
        return false

    }



}
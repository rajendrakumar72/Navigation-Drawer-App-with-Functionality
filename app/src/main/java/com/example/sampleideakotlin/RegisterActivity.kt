package com.example.sampleideakotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleideakotlin.fragment.BottomSheet


class RegisterActivity : AppCompatActivity() {

    companion object{
        lateinit var credentials: Credentials
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val edRegUserName=findViewById<EditText>(R.id.edtRegUserName)
        val edRegUserPassword=findViewById<EditText>(R.id.edtRegUserPassword)
        val edRegUserEmail=findViewById<EditText>(R.id.edtRegUserEmail)
        val cbRadioGroup=findViewById<RadioGroup>(R.id.radioGroup)

        val btnRegister=findViewById<Button>(R.id.btnRegister)

        val selectedId=cbRadioGroup.checkedRadioButtonId
        val rbSex=findViewById<RadioButton>(selectedId)

        val sharedPreferences:SharedPreferences=getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedPreferences.edit()

        btnRegister.setOnClickListener(View.OnClickListener {

            val regUserName = edRegUserName.text.toString()
            val regUserPassword = edRegUserPassword.text.toString()
            val regUserEmail = edRegUserEmail.text.toString()
            val sexGroup = rbSex.text.toString()
            if (validate(regUserName, regUserPassword, regUserEmail) &&checkPasswordLength(regUserPassword)) {

                credentials = Credentials(regUserName, regUserPassword, regUserEmail, sexGroup)

                //Store Data
                editor.putString("userName", regUserName)
                editor.putString("userPassword", regUserPassword)
                editor.putString("userEmail", regUserEmail)
                editor.putString("userSex", sexGroup)
                editor.putBoolean("isUserLogin", true)

                //Commit the Changes and adds them to the files
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    finish()
            }


        })


    }

    private fun validate(userName: String, userPassword: String, userEmail: String):Boolean{
        if (userName.isEmpty()||userPassword.isEmpty()||userEmail.isEmpty()){
            Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkPasswordLength(pass:String):Boolean{
        if (pass.length<8){
            Toast.makeText(this,"Please Enter Password Minimum 8 character ",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
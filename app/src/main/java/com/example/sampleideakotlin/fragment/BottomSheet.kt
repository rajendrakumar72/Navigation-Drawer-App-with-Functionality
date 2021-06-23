package com.example.sampleideakotlin.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.sampleideakotlin.Credentials
import com.example.sampleideakotlin.R
import com.example.sampleideakotlin.sharedPrefFile
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.Exception
import kotlin.math.log

class BottomSheet():BottomSheetDialogFragment() {
    private lateinit var  txtBoUName:TextView
    private lateinit var txtBoUNEmail:TextView
    private lateinit var txtBoUPass:TextView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.bottom_sheet, container, false)

     txtBoUName  =v.findViewById<TextView>(R.id.txtBottomUserName)
        txtBoUNEmail =v.findViewById<TextView>(R.id.btnBottomUserEmail)
        txtBoUPass =v.findViewById<TextView>(R.id.bottomUserPassword)

        val bundle= arguments
        val userName=bundle?.getString("username")
        val userEmail=bundle?.getString("useremail")
        val userPass=bundle?.getString("userpassword")
//        val sharedPreferences=context?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
//        val savedUserName: String? =sharedPreferences?.getString("userName","")
//        val savedUserPassword:String?=sharedPreferences?.getString("userPassword","")
//        val savedUserEmail:String?=sharedPreferences?.getString("userEmail","")




                Log.d(TAG, "onCreateView: $userName" )
                txtBoUName.text = userName
                txtBoUPass.text =userPass
                txtBoUNEmail.text = userEmail


        return v

    }





}
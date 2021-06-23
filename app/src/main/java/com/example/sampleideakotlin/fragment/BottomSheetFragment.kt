package com.example.sampleideakotlin.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.sampleideakotlin.R
import com.example.sampleideakotlin.sharedPrefFile
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class BottomSheetFragment : Fragment() {
    lateinit var bottomButton:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

       bottomButton=v.findViewById<Button>(R.id.buttonToggleBottomSheet)


        bottomButton.setOnClickListener(View.OnClickListener {
           // toggleBottomSheet()
            passData()
        })

        // Inflate the layout for this fragment
        return v
    }


private fun toggleBottomSheet(){
    val view=layoutInflater.inflate(R.layout.bottom_sheet,null)

    val linearLayout=view.findViewById<LinearLayout>(R.id.bottomSheet)

    val bottomSheetDialog= activity?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(view)
        bottomSheetDialog?.show()
         passData()

}

    private fun passData(){
        val sharedPreferences=context?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val savedUserName: String? =sharedPreferences?.getString("userName","")
        val savedUserPassword:String?=sharedPreferences?.getString("userPassword","")
        val savedUserEmail:String?=sharedPreferences?.getString("userEmail","")

        val bundle=Bundle()
        bundle.putString("username",savedUserName)
        bundle.putString("userpassword",savedUserPassword)
        bundle.putString("useremail",savedUserEmail)
        val bottomSheet= BottomSheet()
        bottomSheet.arguments=bundle
        bottomSheet.show(parentFragmentManager,bottomSheet.tag)
    }



}
package com.example.sampleideakotlin.fragment

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sampleideakotlin.R


class BroadcastFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_broadcast, container, false)

        val edtName=v.findViewById<EditText>(R.id.broadCastName)
        val edtSubject=v.findViewById<EditText>(R.id.broadCastSubject)
        val edtBody=v.findViewById<EditText>(R.id.broadCastBody)

        val btnPush=v.findViewById<Button>(R.id.btnPushNotification)

        btnPush.setOnClickListener(View.OnClickListener {
            val name = edtName.text.toString().trim()
            val body = edtBody.text.toString().trim()
            val subject = edtSubject.text.toString().trim()
            if (name.isEmpty() || body.isEmpty() ||subject.isEmpty() ){
                Toast.makeText(context,"Please Enter All Field",Toast.LENGTH_SHORT).show()
            }else {

                val manager = requireActivity()!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notify = Notification.Builder(activity).setContentTitle(name).setContentText(body)
                        .setContentTitle(subject).setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                        .build()

                notify.flags = notify.flags or Notification.FLAG_AUTO_CANCEL
                manager.notify(0, notify)
            }

        })


        return v
    }

   
}
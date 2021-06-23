package com.example.sampleideakotlin.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.sampleideakotlin.R




class ServiceFragment : Fragment() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private  var handler: Handler=Handler()
    private var pause:Boolean=false
    private lateinit var seekBar: SeekBar
    private lateinit var  txtPass:TextView
    private lateinit var txtDue:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_service_player, container, false)

        val playBtn=v.findViewById<Button>(R.id.playBtn)
        val pauseBtn=v.findViewById<Button>(R.id.pauseBtn)
        val stopBtn=v.findViewById<Button>(R.id.stopBtn)

         txtPass=v.findViewById<TextView>(R.id.tv_pass)
         txtDue=v.findViewById<TextView>(R.id.tv_due)

         seekBar=v.findViewById<SeekBar>(R.id.seek_bar)

        // Start the media player
        playBtn.setOnClickListener {
            if (pause){
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause=false
                Toast.makeText(requireActivity(),"Media Playing",Toast.LENGTH_SHORT).show()
            }else{
                mediaPlayer= MediaPlayer.create(activity,R.raw.kadhal_naigara)
                mediaPlayer.start()
                Toast.makeText(activity,"Media Playing",Toast.LENGTH_SHORT).show()

            }
            initializeSeekBar()
            playBtn.isEnabled=false
            pauseBtn.isEnabled=true
            stopBtn.isEnabled=true

            mediaPlayer.setOnCompletionListener {
                playBtn.isEnabled=true
                pauseBtn.isEnabled=false
                stopBtn.isEnabled=false
                Toast.makeText(context,"End",Toast.LENGTH_SHORT).show()
            }
        }

        // Pause the media player
        pauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                pause=true
                playBtn.isEnabled=true
                pauseBtn.isEnabled=false
                stopBtn.isEnabled=true
                Toast.makeText(context,"Media Pause",Toast.LENGTH_SHORT).show()
            }
        }

        // Stop the media player
        stopBtn.setOnClickListener {
            if (mediaPlayer.isPlaying || pause){
                pause=false
                seekBar.progress = 0
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                playBtn.isEnabled=true
                pauseBtn.isEnabled=false
                stopBtn.isEnabled=false
                txtPass.text="00:00"
                txtDue.text="00:00"
                Toast.makeText(context,"Media Stop",Toast.LENGTH_SHORT).show()
            }
        }
        // Seek bar change listener
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.seekTo(progress*1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })



        return v
    }

    private fun initializeSeekBar(){
       seekBar.max=mediaPlayer.seconds

        runnable= Runnable {
            seekBar.progress=mediaPlayer.currentSeconds
            txtPass.text="${mediaPlayer.currentSeconds}"
            val diff=mediaPlayer.seconds - mediaPlayer.currentSeconds
            txtDue.text="$diff"

            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }

}
// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }

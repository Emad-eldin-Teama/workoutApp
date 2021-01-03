package com.example.thesevenmmnutesworkout

import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts:TextToSpeech?= null
    private var player: MediaPlayer? = null
    private var exerciseAdaptor :ExerciseStatusAdaptor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
            tts= TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        setupExerciseStatusRecyclerView ()

    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if(tts!= null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!= null)
            player!!.stop()

        super.onDestroy()
    }

    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdaptor!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < (exerciseList?.size!! - 1)) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdaptor!!.notifyDataSetChanged()
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations! you finished the workout", Toast.LENGTH_LONG).show()

                }
            }
        }.start()
    }

    private fun setupRestView() {
       try {
           player = MediaPlayer.create(applicationContext,R.raw.press_start)
           player!!.isLooping=false
           player!!.start()
       }
       catch(e:Exception){
           e.printStackTrace()
       }

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupExerciseView() {
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

    }

    override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS){
        val result = tts!!.setLanguage(Locale.US)
        if(result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED)
    Log.e("TTS","the language specified is not supported")
    }else{
      Log.e("TTS","Text to speech failed!")
    }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut (text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
 private fun setupExerciseStatusRecyclerView (){
     rvExerciseStatus.layoutManager = LinearLayoutManager(this,
             LinearLayoutManager.HORIZONTAL,false)
     exerciseAdaptor = ExerciseStatusAdaptor(exerciseList!!,this)
     rvExerciseStatus.adapter = exerciseAdaptor
 }

}



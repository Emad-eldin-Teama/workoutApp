package com.example.thesevenmmnutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
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

            override fun onFinish() {
                currentExercisePosition++
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
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations! you finished the workout", Toast.LENGTH_LONG).show()

                }
            }
        }.start()
    }

    private fun setupRestView() {
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setupExerciseView() {
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }


        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }


}



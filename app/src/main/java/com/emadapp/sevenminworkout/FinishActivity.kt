package com.emadapp.sevenminworkout

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.emadapp.sevenminworkout.R
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*


//START
class FinishActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        //
        //START
        setSupportActionBar(toolbar_finish_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        //END


        //START
        btnFinish.setOnClickListener {
            finish()

        }
        addDateToDataBase()
    }
   @RequiresApi(Build.VERSION_CODES.N)
   private fun addDateToDataBase(){
       val calender = Calendar.getInstance()
       val dateTime = calender.time
       Log.i("DATE",""+ dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
       val dbHandler = SqliteOpenHelper(this, null)
       dbHandler.addDate(date) // Add date function is called.
       Log.e("Date : ", "Added...")

   }
}
//END
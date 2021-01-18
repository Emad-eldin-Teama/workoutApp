package com.example.thesevenmmnutesworkout

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        val actionbar = supportActionBar
        if (actionbar !=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "History"

        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }
    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDatesList = dbHandler.getAllCompletedDatesList()
       if(allCompletedDatesList.size > 0){
           tvHistory.visibility = View.VISIBLE
           rvHistory.visibility=View.VISIBLE
           tvNoDataAvailable.visibility= View.GONE
           rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter (this,allCompletedDatesList)
        rvHistory.adapter = historyAdapter
       } else{
           tvHistory.visibility = View.GONE
           rvHistory.visibility=View.GONE
           tvNoDataAvailable.visibility= View.VISIBLE
       }

    }
}
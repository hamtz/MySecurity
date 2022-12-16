package com.ilham.mysecurity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jumlahClassNames = findViewById<TextView>(R.id.tv_jumlah_class_names)
        val realtimeDetect = findViewById<TextView>(R.id.tv_realtime_detection)
        val realtimeTime = findViewById<TextView>(R.id.tv_realtime_time)

        val database = FirebaseDatabase.getInstance()
        val classNamesRef = database.getReference("classNames")
        val detectedRef = database.getReference("detected")

        // Read from the database
        classNamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val size = dataSnapshot.childrenCount.toInt()
                jumlahClassNames.text = size.toString()
//                val value = dataSnapshot.getValue(String::class.java)
//                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        detectedRef.addValueEventListener(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currDetect = dataSnapshot.child("name").getValue(String::class.java)
                realtimeDetect.text = currDetect.toString()
//                val value = dataSnapshot.getValue(String::class.java)
//                Log.d(TAG, "Value is: $value")
                val currTimeDetect = dataSnapshot.child("time").getValue(Int::class.java)
                val tolong = currTimeDetect?.toLong()
//                val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val dt = tolong?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }
                realtimeTime.text = dt.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.about -> {
                Toast.makeText(this,"About",Toast.LENGTH_SHORT).show()
                val i = Intent(this, AboutActivity::class.java)
                this.startActivity(i)
            }
            R.id.settings ->{
                Toast.makeText(this,"Detection History",Toast.LENGTH_SHORT).show()
                val i = Intent(this, HistoryActivity::class.java)
                this.startActivity(i)
            }
            R.id.exit ->{
                Toast.makeText(this,"Exit",Toast.LENGTH_SHORT).show()
//                val i = Intent(this, AboutActivity::class.java)
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
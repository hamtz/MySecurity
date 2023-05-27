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
import com.google.firebase.messaging.FirebaseMessaging
import java.time.Instant
import java.time.ZoneId


class MainActivity : AppCompatActivity() {

//    companion object {
//        private const val TAG = "MainActivity"
//        private const val NOTIFICATION_REQUEST_CODE = 1234
//    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val CHANNEL_ID = "1"


//        val jumlahClassNames = findViewById<TextView>(R.id.tv_jumlah_class_names)
        val realtimeDetect = findViewById<TextView>(R.id.tv_realtime_detection)
        val realtimeTime = findViewById<TextView>(R.id.tv_realtime_time)

        val database = FirebaseDatabase.getInstance()
        val classNamesRef = database.getReference("classNames")
        val detectedRef = database.getReference("detected")

        // Read from the database
        classNamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val size = dataSnapshot.childrenCount.toInt()
//                jumlahClassNames.text = size.toString() # sementara di non aktifkan

//                <TextView
//                android:id="@+id/tx_jumlah_known_name"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:gravity="center"
//                android:text="Jumlah Orang Terdaftar"
//                android:textAllCaps="true"
//                android:textSize="24sp"
//                android:textStyle="bold"
//                android:textColor="@color/black"/>
//
//                <TextView
//                android:id="@+id/tv_jumlah_class_names"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:gravity="center"
//                android:text="0"
//                android:textSize="18sp"
//                android:textStyle="bold" />
//
//                val value = dataSnapshot.getValue(String::class.java)
//                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        //content history
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
//                notification()
//                Log.d(realtimeTime.text)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        //GET TOKEN ID FOR FIREBASE
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                var fbToken = result
                Log.d(TAG,"onComplete: Token: "+ fbToken);
                // DO your thing with your firebase token
            }
        }

//        <NOTIFICATION> ===================================================
        // Create an explicit intent for an Activity in your app
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
////
//        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_stat_ic_notification)
//            .setContentTitle("My notification")
//            .setContentText("Much longer text that cannot fit one line...")
//            .setStyle(NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            val notificationId = (0..99).random()
//            notify(notificationId, builder.build())
//        }
//        <END OF NOTIFICATION>========================================

//        createNotificationChannel()
    }

//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//
//            val CHANNEL_ID = (0..10).random().toString()
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

//      SIMPLE NOTIFICATION
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun notification() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel("n", "n", NotificationManager.IMPORTANCE_HIGH)
//            val manager = getSystemService(NotificationManager::class.java)
//            manager.createNotificationChannel(channel)
////        }
//
//        val builder = NotificationCompat.Builder(this,"n").setContentText("Code Sphere").setSmallIcon(R.drawable.ic_settings).setAutoCancel(true).setContentText("New Data is added:")
//        val managerCompat = NotificationManagerCompat.from(this)
//        managerCompat.notify(999,builder.build())
//
//    }

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
            } R.id.detections -> {
                Toast.makeText(this,"Face Detected",Toast.LENGTH_SHORT).show()
                val i = Intent(this, ImageDetectedActivity::class.java)
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
package com.ilham.mysecurity

import ImageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class ImageDetectedActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detected)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengambil referensi Firebase Storage
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        // Mendapatkan daftar file dari Firebase Storage
        val listRef: StorageReference = storageRef.child("detected_faces/static/detected/")
        val listTask: Task<ListResult> = listRef.listAll()

        listTask.addOnSuccessListener { listResult ->
            val imageItems = mutableListOf<ImageItem>()
            for (item in listResult.items) {
                // Mendapatkan URL gambar dari setiap item
                item.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    val imageItem = ImageItem(imageUrl)
                    imageItems.add(imageItem)
                    adapter.notifyDataSetChanged()
                }
            }
            adapter = ImageAdapter(this, imageItems)
            recyclerView.adapter = adapter
        }
    }
}
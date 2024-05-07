package com.example.musicstream

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicstream.adapter.SearchSongRecyclerAdapter
import com.example.musicstream.databinding.ActivitySearchSongBinding
import com.example.musicstream.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore

class SearchSongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchSongBinding
    private lateinit var adapter: SearchSongRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.searchSongnameInput.requestFocus()

        binding.backBtn.setOnClickListener { onBackPressed() }

        binding.searchSongBtn.setOnClickListener {
            val searchTerm = binding.searchSongnameInput.text.toString()
            if (searchTerm.isEmpty() || searchTerm.length < 3) {
                binding.searchSongnameInput.error = "Invalid Search Term"
                return@setOnClickListener
            }
            searchSongs(searchTerm)
        }
    }



    private fun searchSongs(searchTerm: String) {
        val query = FirebaseFirestore.getInstance().collection("songs")
            .whereGreaterThanOrEqualTo("title", searchTerm)
            .whereLessThanOrEqualTo("title", searchTerm + '\uf8ff')

        query.get().addOnSuccessListener { documents ->
            val songList = mutableListOf<SongModel>()
            for (document in documents) {
                val song = document.toObject(SongModel::class.java)
                songList.add(song)
            }
            setupRecyclerView(songList)
        }.addOnFailureListener {
            // Handle failure
        }
    }

    private fun setupRecyclerView(songList: List<SongModel>) {
        val songTitles = songList.map { it.title }
        adapter = SearchSongRecyclerAdapter(songList)
        binding.searchSongRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchSongRecyclerView.adapter = adapter
    }

}

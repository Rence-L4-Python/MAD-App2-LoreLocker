package com.example.lorelocker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lorelocker.databinding.ActivityDetailVaultBinding

class DetailVault : AppCompatActivity() {

    private lateinit var binding: ActivityDetailVaultBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vaultName = "detail_vault"
        db = NotesDatabaseHelper(this, vaultName)
        db.tableValidation()

        notesAdapter = NotesAdapter(db.getAllNotes(), this, "detail_vault")
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("vault", vaultName)
            startActivity(intent)
        }

        val homenav = findViewById<ImageButton>(R.id.homebtn)
        val worldnav = findViewById<ImageButton>(R.id.worldbtn)
        val lorenav = findViewById<ImageButton>(R.id.lorebtn)
        val detailnav = findViewById<ImageButton>(R.id.detailbtn)
        val storynav = findViewById<ImageButton>(R.id.storybtn)

        val worldvaultchange = View.OnClickListener {
            val intent = Intent(this, WorldVault::class.java)
            startActivity(intent)
        }
        val lorevaultchange = View.OnClickListener {
            val intent = Intent(this, LoreVault::class.java)
            startActivity(intent)
        }
        val detailvaultchange = View.OnClickListener {
            val intent = Intent(this, DetailVault::class.java)
            startActivity(intent)
        }
        val storyvaultchange = View.OnClickListener {
            val intent = Intent(this, StoryVault::class.java)
            startActivity(intent)
        }
        homenav.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        worldnav.setOnClickListener(worldvaultchange)
        lorenav.setOnClickListener(lorevaultchange)
        detailnav.setOnClickListener(detailvaultchange)
        storynav.setOnClickListener(storyvaultchange)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}
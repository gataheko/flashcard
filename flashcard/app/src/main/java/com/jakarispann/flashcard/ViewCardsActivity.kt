package com.jakarispann.flashcard

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewCardsActivity : AppCompatActivity() {

    private lateinit var lvCards: ListView
    private lateinit var tvEmpty: TextView
    private lateinit var dbHelper: FlashcardDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cards)

        lvCards = findViewById(R.id.lvCards)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Database: read all saved flashcards from SQLite on activity launch
        dbHelper = FlashcardDbHelper(this)
        loadCards()
    }

    // Database: load and display all flashcards from the database
    private fun loadCards() {
        val cards = dbHelper.getAllCards()

        if (cards.isEmpty()) {
            tvEmpty.visibility = android.view.View.VISIBLE
            lvCards.visibility = android.view.View.GONE
        } else {
            tvEmpty.visibility = android.view.View.GONE
            lvCards.visibility = android.view.View.VISIBLE

            // Format each card as "Q: ... | A: ..." for display
            val displayList = cards.map { (q, a) -> "Q: $q\nA: $a" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
            lvCards.adapter = adapter
        }
    }
}

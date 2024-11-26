package com.example.casafantasmas

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.casafantasmas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding
    lateinit var gridLayout: GridLayout
    lateinit var cardView: CardView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initGridLayout()

        //Acceder a una tarjeta en concreto
        val card1 = gridLayout.getChildAt(0) as CardView
        //Acceder al texto de dentro de la tarjeta
        val text1 = card1.findViewById<TextView>(R.id.textView)




    }

    private fun initGridLayout() {
        for (i in 1..16) {
            cardView = LayoutInflater.from(this).inflate(R.layout.card, gridLayout, false) as CardView
            textView = cardView.findViewById<TextView>(R.id.textView)
            textView.text = i.toString()
            gridLayout.addView(cardView)
        }
    }

    private fun initComponents() {
        mibinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)
        gridLayout = mibinding.gridLayout
    }
}
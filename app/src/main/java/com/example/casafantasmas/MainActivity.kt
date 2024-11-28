package com.example.casafantasmas


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.casafantasmas.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding
    lateinit var gridLayout: GridLayout
    lateinit var cardView: CardView
    lateinit var imageView: ImageView
    val randomCardStart: Int = (0..15).random()
    val randomCardFinish: Int = (0..15).random()
    val board = Array(4) { arrayOfNulls<CardView>(4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initGridLayout()
        assingCardsToArray()

        //Acceder a una tarjeta en concreto
        val card1 = gridLayout.getChildAt(0) as CardView
        //Acceder a la imagen de dentro de la tarjeta
        val image1 = card1.findViewById<ImageView>(R.id.imageView)


    }

    private fun assingCardsToArray() {
        for(row in 0 until 4) {
            for (column in 0 until 4) {
                val index = row * 4 + column
                val cardView = gridLayout.getChildAt(index) as CardView
                board[row][column] = cardView
                Log.d("CardAssignment", "board[$row][$column] = $cardView")
            }
        }
    }

    private fun initGridLayout() {
        for (i in 1..16) {
            cardView = LayoutInflater.from(this).inflate(R.layout.card, gridLayout, false) as CardView

            imageView = cardView.findViewById(R.id.imageView)

            imageView.setImageResource(R.drawable.ic_launcher_background)

            gridLayout.addView(cardView)
        }
        initStartFinish()
    }

    private fun initStartFinish() {

        val cardStart = gridLayout.getChildAt(randomCardStart) as CardView
        val imageStart = cardStart.findViewById<ImageView>(R.id.imageView)
        imageStart.setImageResource(R.drawable.img_1)

        val cardFinish = gridLayout.getChildAt(randomCardFinish) as CardView
        val imageFinish = cardFinish.findViewById<ImageView>(R.id.imageView)
        imageFinish.setImageResource(R.drawable.img)
    }

    private fun initComponents() {
        mibinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)
        gridLayout = mibinding.gridLayout
    }
}
package com.example.casafantasmas


import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initGridLayout()

        //Acceder a una tarjeta en concreto
        val card1 = gridLayout.getChildAt(0) as CardView
        //Acceder a la imagen de dentro de la tarjeta
        val image1 = card1.findViewById<ImageView>(R.id.imageView)

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
        val randomCardStart: Int = (0..15).random()

        val cardStart = gridLayout.getChildAt(randomCardStart) as CardView
        val imageStart = cardStart.findViewById<ImageView>(R.id.imageView)
        imageStart.setImageResource(R.drawable.img_1)

        val randomCardFinish: Int = (0..15).random()
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
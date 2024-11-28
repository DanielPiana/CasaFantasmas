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
        for(row in 0 until 4) { //For para las filas
            for (column in 0 until 4) { //For para las columnas
                val index = row * 4 + column // con row * 4 accedemos a la columna y al sumarle la columna, accedemos a la fila (un indice del
                val cardView = gridLayout.getChildAt(index) as CardView //Accedes a la cardview de esa posicion con el index
                board[row][column] = cardView //Y la guardamos en la posicion del array
                Log.d("CardAssignment", "board[$row][$column] = $cardView")//Log para comprobación
            }
        }
    }

    private fun initGridLayout() {
        for (i in 1..16) { //For para recorrer el gridLayout
            //Creamos la cardView
            cardView = LayoutInflater.from(this).inflate(R.layout.card, gridLayout, false) as CardView
            //Creamos la imageView de dentro de la tarjeta
            imageView = cardView.findViewById(R.id.imageView)
            //Seteamos el fondo predeterminado de las tarjetas
            imageView.setImageResource(R.drawable.ic_launcher_background)
            //Añadimos la vista de la tarjeta al gridLayout
            gridLayout.addView(cardView)
        }
        initStartFinish()
    }

    private fun initStartFinish() {
        //Con un numero aleatorio, lo utilizamos para acceder a la tarjeta de una posicion aleatoria en el gridlayout
        val cardStart = gridLayout.getChildAt(randomCardStart) as CardView
        //con la tarjeta ya podemos inicializar la imagen y poder cambiar el fondo de una, que será el comienzo
        val imageStart = cardStart.findViewById<ImageView>(R.id.imageView)
        imageStart.setImageResource(R.drawable.img_1)

        //Hacmeos lo mismo con otra posicion para el final
        val cardFinish = gridLayout.getChildAt(randomCardFinish) as CardView
        val imageFinish = cardFinish.findViewById<ImageView>(R.id.imageView)
        imageFinish.setImageResource(R.drawable.img)
    }

    private fun initComponents() {//Metodo para inicializar componentes
        mibinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)
        gridLayout = mibinding.gridLayout
    }
}
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
    val board = Array(4) { arrayOfNulls<CardView>(4) }

    //Variables para trackear la posicion del usuario
    lateinit var cardUsuario:CardView
    lateinit var imageUsuario:ImageView

    //Generamos posiciones aleatorias
    var randomRowStart = (0..3).random()
    var randomColumnStart=(0..3).random()
    val randomRowFinish=(0..3).random()
    val randomColumnFinish=(0..3).random()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initGridLayout()
        assingCardsToArray()
        displayMoves()


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
        //Si coinciden en el mismo sitio, o en posiciones adyacentes vertical u horizontal, generamos nuevas posiciones
        while (randomRowStart == randomRowFinish+1 || randomRowStart == randomRowFinish-1 ||
            randomColumnStart == randomColumnFinish+1 || randomColumnStart == randomColumnFinish-1 ||
            (randomRowStart == randomRowFinish && randomColumnStart == randomColumnFinish)) {

            randomRowStart = (0..3).random()
            randomColumnStart = (0..3).random()
            println("randomRowStart " +randomRowStart)
            println("randomColumnStart "+randomColumnStart)
            /*println(numFilaStart)
            println(numColumnaStart)
            println(numFilaFinish)
            println(numColumnaFinish)*/
        }
        //Lo convertimos a indice para el gridlayout
        val cardStartPosition:Int = randomRowStart * 4 + randomColumnStart
        val cardFinishPosition:Int = randomRowFinish * 4 + randomColumnFinish
        /*println(cardStartPosition)
        println(cardFinishPosition)*/
        //Con el indice generado, podemos acceder a la tarjeta de una posicion aleatoria en el gridlayout
        cardUsuario = gridLayout.getChildAt(cardStartPosition) as CardView
        //con la tarjeta ya podemos inicializar la imagen y poder cambiar el fondo de una, que será el comienzo
        imageUsuario = cardUsuario.findViewById<ImageView>(R.id.imageView)
        imageUsuario.setImageResource(R.drawable.img_1)

        //Hacemos lo mismo con otra posicion para el final
        val cardFinish = gridLayout.getChildAt(cardFinishPosition) as CardView
        val imageFinish = cardFinish.findViewById<ImageView>(R.id.imageView)
        imageFinish.setImageResource(R.drawable.img)
    }
    fun displayMoves() {
        //Con respecto a donde ha empezado el usuario, conseguimos las posiciones adyacentes
        var bottomPosition:Int=-1
        var topPosition:Int =-1
        var rightPosition:Int =-1
        var leftPosition:Int =-1

        bottomPosition = (randomRowStart+1) * 4  + randomColumnStart //Adyacente inferior
        topPosition = (randomRowStart-1) * 4 + randomColumnStart //Adyacente superior
        rightPosition = randomRowStart * 4  + (randomColumnStart+1) //Adyacente derecha
        leftPosition = randomRowStart * 4  + (randomColumnStart-1) //Adyacente izquierda

        val listaPosiciones = mutableListOf(bottomPosition,topPosition,rightPosition,leftPosition)
        //Si la columna es 0, ignorar leftPosition
        if (randomColumnStart == 0) listaPosiciones.removeAt(3)
        //Si la columna es 3, ignorar rightPosition
        if (randomColumnStart == 3) listaPosiciones.removeAt(2)
        println(listaPosiciones)
        for (move in listaPosiciones) {
            println(move)
            if (move in 1..16) {//Si el movimiento es mayor a 0 y menor a 16
                val card = gridLayout.getChildAt(move) as CardView
                val image = card.findViewById<ImageView>(R.id.imageView)
                image.setImageResource(R.drawable.img_2)
                // ESTABLEZCO UN LISTENER PARA CUANDO HAGA CLICK A LAS TARJETAS DISPONIBLES PARA MOVERSE
                // VALORAR SACAR A UN METODO
                image.setOnClickListener {
                    it.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).withEndAction {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(150)
                    }
                    var dialog = CustomDialogFragment()
                    dialog.show(supportFragmentManager,"customDialog")

                    //SACAR LA PREGUNTA Y BLOQUEAR EL RESTO DE LA APLICACION HASTA QUE RESPONDA
                    //SI RESPONDE BIEN, LLAMAR A ESTE METODO OTRA VEZ Y SI NO, VOLVER A SACAR UNA PREGUNTA
                }
            }
        }
    }





    private fun initComponents() {//Metodo para inicializar componentes
        mibinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)
        gridLayout = mibinding.gridLayout
    }
}
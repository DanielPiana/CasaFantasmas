package com.example.casafantasmas


import Question
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.forEachIndexed
import com.example.casafantasmas.databinding.ActivityMainBinding
/*Guardar en una lista las posiciones por las que ha pasado el usuario
*
* */


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

    //VARIABLES PARA LA POSICION INICIAL DEL USUARIO Y DEL FINAL
    var cardStartPosition:Int = 0
    var cardFinishPosition:Int = 0

    // VARIABLES PARA GUARDAR POSICIONES ADYACENTES ANTIGUAS
    lateinit var listaPosiciones:MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initGridLayout()
        assingCardsToArray()
        displayMoves(randomRowStart,randomColumnStart)

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
        }
        //Lo convertimos a indice para el gridlayout
        cardStartPosition = randomRowStart * 4 + randomColumnStart
        cardFinishPosition = randomRowFinish * 4 + randomColumnFinish
        println("cardStartPosition $cardStartPosition")
        updateUserPosition(cardStartPosition)
    }

    fun updateUserPosition(position:Int) {
        //Con el indice generado, podemos acceder a la tarjeta de una posicion en el gridlayout
        cardUsuario = gridLayout.getChildAt(position) as CardView

        //con la tarjeta ya podemos inicializar la imagen del usuario
        imageUsuario = cardUsuario.findViewById(R.id.imageView)
        imageUsuario.setImageResource(R.drawable.img_1)

        //Hacemos lo mismo con otra posicion para el final
        val cardFinish = gridLayout.getChildAt(cardFinishPosition) as CardView
        val imageFinish = cardFinish.findViewById<ImageView>(R.id.imageView)
        imageFinish.setImageResource(R.drawable.img)
    }


    fun displayMoves(row:Int,column:Int) {
        //Con respecto a donde ha empezado el usuario, conseguimos las posiciones adyacentes
        var bottomPosition:Int=-1
        var topPosition:Int =-1
        var rightPosition:Int =-1
        var leftPosition:Int =-1

        bottomPosition = (row+1) * 4  + column //Adyacente inferior
        topPosition = (row-1) * 4 + column //Adyacente superior
        rightPosition = row * 4  + (column+1) //Adyacente derecha
        leftPosition = row * 4  + (column-1) //Adyacente izquierda

        println("bottomPosition $bottomPosition")
        println("topPosition $topPosition")
        println("rightPosition $rightPosition")
        println("leftPosition $leftPosition")



        listaPosiciones = mutableListOf(bottomPosition,topPosition,rightPosition,leftPosition)
        //Si la columna es 0, ignorar leftPosition
        if (column == 0) listaPosiciones.removeAt(3)
        //Si la columna es 3, ignorar rightPosition
        if (column == 3) listaPosiciones.removeAt(2)
        // HAGO UN ITERATOR PARA PODER MODIFICAR UNA LISTA MIENTRAS LA RECORRO
        val iterator = listaPosiciones.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            println("move $move")
            var answer = ""
            var question:Question<*>
            if (move in 0..15) {//Si el movimiento es mayor a 0 y menor a 16
                println("move filtrado $move")
                val card = gridLayout.getChildAt(move) as CardView
                val image = card.findViewById<ImageView>(R.id.imageView)
                image.setImageResource(R.drawable.img_2)
                // ESTABLEZCO UN LISTENER PARA CUANDO HAGA CLICK A LAS TARJETAS DISPONIBLES PARA MOVERSE
                image.setOnClickListener {

                    // CONFIGURACION DEL DIALOG FRAGMENT
                    val dialog = CustomDialogFragment()
                    val args = Bundle()
                    question = Question.randomQuestion()
                    args.putString("question_text", question.questionText)
                    dialog.arguments = args

                    // LISTENER PARA EL RESULT DEL DIALOG FRAGMENT PASANDO PARAMETROS POR BUNDLE
                    supportFragmentManager.setFragmentResultListener("dialog_result",this) {_, bundle ->
                        answer = bundle.getString("answer", "")
                        if (question.checkAnswer(answer)) {
                            imageUsuario.setImageResource(R.drawable.img_3)
                            // GUARDAMOS LA POSICION DEL USUARIO
                            imageUsuario = image
                            cardUsuario = card
                            resetOldPositions()
                            image.setImageResource(R.drawable.img_1)
                            val newRow = move / 4
                            val newColumn = move % 4
                            //setear antiguos movimientos a imagen predeterminada
                            println(listaPosiciones)
                            displayMoves(newRow,newColumn)
                            //cambiar antiguas posiciones a fondo predeterminado
                        } else  {
                            Toast.makeText(this, "Respuesta incorrecta, prueba otra vez", Toast.LENGTH_SHORT).show()

                        }
                    }

                    dialog.show(supportFragmentManager,"customDialog")
                }
            } else {
                // ELIMINAMOS EL ELEMENTO, PARA QUE NO DE ERROR AL USAR EL METODO resetOldPositions
                // POR INTENTAR ACCEDER A UN INDEX DEL LAYOUT QUE NO EXISTE
                println("elemento eliminado $move")
                iterator.remove()
            }
        }
    }
    fun resetOldPositions() {
        for (i in listaPosiciones) {
            val card = gridLayout.getChildAt(i) as CardView
            val image = card.findViewById<ImageView>(R.id.imageView)
            image.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun initComponents() {//Metodo para inicializar componentes
        mibinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)
        gridLayout = mibinding.gridLayout
    }
}
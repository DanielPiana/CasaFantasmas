package com.example.casafantasmas

class Question(var questionText:String, var answer:Any,) {

    val questions: List<Question> = listOf(
        Question("¿Cuánto es 2 + 2?", 4),
        Question("¿Capital de Francia?", "París"),
        Question("¿Es Kotlin un lenguaje de programación?", true),
        Question("¿Cuántos días tiene un año bisiesto?", 366),
        Question("¿Color del cielo?", "Azul"),
        Question("¿Cuántos continentes hay en la Tierra?", 7),
        Question("¿Cuál es el idioma oficial de Brasil?", "Portugués"),
        Question("¿Cuántas patas tiene una araña?", 8),
        Question("¿En qué año llegó el hombre a la Luna?", 1969),
        Question("¿Qué gas respiramos principalmente los seres humanos?", "Oxígeno"),
        Question("¿Cuál es el océano más grande del mundo?", "Pacífico"),
        Question("¿Qué planeta es conocido como el planeta rojo?", "Marte"),
        Question("¿Cuántos minutos tiene una hora?", 60),
        Question("¿Qué instrumento mide la temperatura?", "Termómetro"),
        Question("¿Quién escribió *Don Quijote de la Mancha*?", "Miguel de Cervantes")
    )

    fun checkAnswer(answer: Any):Boolean {
        return answer == this.answer
    }

    fun randomQuestion():Question {
        return questions.random()
    }
}
package com.example.casafantasmas

class Question(var questionText:String, var answer:Any,) {


    fun checkAnswer(answer: Any):Boolean {
        return answer == this.answer
    }


}
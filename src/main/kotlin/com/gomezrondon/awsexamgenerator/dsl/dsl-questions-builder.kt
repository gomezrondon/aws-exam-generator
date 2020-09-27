package com.gomezrondon.awsexamgenerator.dsl

import com.gomezrondon.awsexamgenerator.utils.getAlfabetList
import com.gomezrondon.gcpexamgenerator.entities.txtQuestion
import java.io.File


/**
 * DSL that helps assemble the question
 */
object work{
    var build = createCommandQuestion{

        fileName(
                "questions.txt"
       //"commands-questions.txt"
        )

        level(
                " e  ".trim()
        )
        question(
                """
You ONLY want to manage Applications and Data. Which type of Cloud Computing model should you use?
   
            """
        )

        options( """
*On-premises
*Infrastructure as a Service (laaS)
*Software as a Service (SaaS)
*Platform as a Service (PaaS)
             """)

        answer( """
d*Platform as a Service (PaaS)
         
            """)
    }

}



fun createCommandQuestion( c: QuestionBuilder.()-> Unit): txtQuestion {
    val builder = QuestionBuilder()
    c(builder)
    return builder.build(builder.file)
}

class QuestionBuilder{

    var file:String= "questions.txt"
    var question:String?= null
    var option: Map<String, String> = LinkedHashMap()
    var answer: Map<String, String> = LinkedHashMap()
    var level: String="m"

    fun fileName(file: String) {
        this.file = file
    }

    fun level(level: String) {
     this.level = level
    }

    fun question(question:String){
        this.question = question.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .capitalize()
    }

    fun options(options: String) {

        val optionsList = getAlfabetList()

        var temp = mutableMapOf<String, String>()
         options.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .split("""(?=([a-z-A-z]|\s)\*)""".toRegex(RegexOption.MULTILINE))
                .filter { !it.isNullOrEmpty() }
                .map { it.capitalize() }
                .forEachIndexed { index, it ->
                    val split = it.split("*")

                   val letter =  if (split.get(0).trim().isNullOrEmpty()) {
                       optionsList.get(index)
                    }else{
                       split.get(0)
                   }

                    temp.put(letter, split.get(1))
                }

        this.option = temp
    }

    fun answer(answer: String) {
        var temp = mutableMapOf<String, String>()

        val split = answer.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .split("*")

        temp.put(split.get(0).toUpperCase(), split.get(1).capitalize())

        this.answer = temp
    }


    fun build(q_file: String):txtQuestion {

        val lastCount = getLastQuestionCount(q_file) + 1

        question = question?.replace("\n","")
        val options = option.map { it.key.toUpperCase() + ". " + it.value.trim().capitalize()}.joinToString(" ")

        val qText = lastCount.toString() + "~" +level+ "~" +question + " "+ options

        val answer = answer.map { it.key.toUpperCase() + "~" + it.value.trim().capitalize()}.joinToString()
        val aText = lastCount.toString() + "~" + answer

        return txtQuestion(qText, aText)
    }

    private fun getLastQuestionCount( q_file:String): Int {
        val lastCount = File("questions/"+q_file).readLines()
                .filter { !it.startsWith("---") }
                .filter { it.isNotEmpty() }
                .takeLast(1)
                .map { it.split("~").get(0).toInt() }
                .first()
        return lastCount
    }



}








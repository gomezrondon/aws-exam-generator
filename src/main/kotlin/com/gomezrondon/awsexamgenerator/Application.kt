package com.gomezrondon.awsexamgenerator


import com.gomezrondon.awsexamgenerator.service.GenerateQuestionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Application(val service: GenerateQuestionService): CommandLineRunner {

	val LOG: Logger = LoggerFactory.getLogger(Application::class.java);

	override fun run(vararg args: String?) {
		val menu ="""
        Select  
        1) for All regular questions. 
        
        R?:
    """.trimIndent()

		println(menu)
		LOG.info(menu)

		val numberOption = readLine().toString().toLowerCase()

		val text = "Option selected: $numberOption \n"
		println(text)
	 	LOG.info(text)


		val keepOrder = false
		val questionsfileName = "questions.txt"
		val answerfileName = "answers.txt"

		when (numberOption) {

			"1" -> {
		 		val questionsList = service.loadQuestions(questionsfileName, answerfileName)
				val output = service.generateQuestions(questionsList, questionsfileName, answerfileName, keepOrder)
				println(output)
				LOG.info(output)
			}

		}



	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


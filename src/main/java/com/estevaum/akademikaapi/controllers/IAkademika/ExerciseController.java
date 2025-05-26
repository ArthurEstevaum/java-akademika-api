package com.estevaum.akademikaapi.controllers.IAkademika;

import com.estevaum.akademikaapi.enums.Difficulty;
import com.estevaum.akademikaapi.outputs.Quiz.Quiz;
import com.estevaum.akademikaapi.services.iakademika.QuizGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

    private final QuizGenerator quizGenerator;

    public ExerciseController(QuizGenerator quizGenerator) {
        this.quizGenerator = quizGenerator;
    }

    @GetMapping("/exercise/generate")
    public ResponseEntity<Quiz> getExercise(@RequestParam(value = "topic", defaultValue = "Primeira guerra mundial") String topic, @RequestParam(value = "difficulty", defaultValue = "MEDIUM") Difficulty difficulty) {
        Quiz quiz = quizGenerator.generateQuiz(topic, difficulty);

        return ResponseEntity.ok(quiz);
    }
}
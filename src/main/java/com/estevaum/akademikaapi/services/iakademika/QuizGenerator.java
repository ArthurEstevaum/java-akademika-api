package com.estevaum.akademikaapi.services.iakademika;

import com.estevaum.akademikaapi.enums.Difficulty;
import com.estevaum.akademikaapi.outputs.Quiz.Quiz;

public interface QuizGenerator {
    public Quiz generateQuiz(String topic, Difficulty difficulty);
}
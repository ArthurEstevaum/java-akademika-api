package com.estevaum.akademikaapi.outputs.Quiz;

import com.estevaum.akademikaapi.enums.OptionLetter;

import java.util.HashMap;
import java.util.List;

public record Exercise(String questionName, List<Option> options, OptionLetter correctAnswerLetter, HashMap<OptionLetter, String> explanations) {
}

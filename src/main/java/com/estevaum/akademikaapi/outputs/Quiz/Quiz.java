package com.estevaum.akademikaapi.outputs.Quiz;

import java.util.List;

public record Quiz(String topic, List<Exercise> exercises) {
}

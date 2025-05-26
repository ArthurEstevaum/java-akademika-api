package com.estevaum.akademikaapi.controllers.IAkademika;

import com.estevaum.akademikaapi.services.iakademika.PromptProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    private final PromptProcessor promptProcessor;

    public PromptController(PromptProcessor promptProcessor) {
        this.promptProcessor = promptProcessor;
    }

    @PostMapping("/prompt/process")
    public ResponseEntity<String> getPromptResponse(@RequestBody String prompt) {
        String promptResponse = promptProcessor.getPromptResponse(prompt);

        return ResponseEntity.ok(promptResponse);
    }
}

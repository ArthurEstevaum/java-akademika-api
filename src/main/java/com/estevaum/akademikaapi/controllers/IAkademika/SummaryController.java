package com.estevaum.akademikaapi.controllers.IAkademika;

import com.estevaum.akademikaapi.outputs.Summary.Summary;
import com.estevaum.akademikaapi.services.iakademika.SummaryGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryController {
    private final SummaryGenerator summaryGenerator;

    public SummaryController(SummaryGenerator summaryGenerator) {
        this.summaryGenerator = summaryGenerator;
    }

    @GetMapping("/summary/generate")
    public ResponseEntity<Summary> getSummary(@RequestParam String topic, @RequestParam String size)  {
        Summary summary = summaryGenerator.generateSummary(topic, size);

        return ResponseEntity.ok(summary);
    }
}

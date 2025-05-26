package com.estevaum.akademikaapi.services.iakademika;

import com.estevaum.akademikaapi.outputs.Summary.Summary;

public interface SummaryGenerator {
    public Summary generateSummary(String topic, String size);
}

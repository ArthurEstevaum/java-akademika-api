package com.estevaum.akademikaapi.services.iakademika;

import com.estevaum.akademikaapi.outputs.PromptResponse;

public interface PromptProcessor {
    public PromptResponse getPromptResponse(String prompt);
}

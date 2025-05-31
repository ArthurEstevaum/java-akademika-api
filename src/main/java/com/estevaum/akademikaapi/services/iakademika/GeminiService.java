package com.estevaum.akademikaapi.services.iakademika;

import com.estevaum.akademikaapi.enums.Difficulty;
import com.estevaum.akademikaapi.outputs.PromptResponse;
import com.estevaum.akademikaapi.outputs.Quiz.Quiz;
import com.estevaum.akademikaapi.outputs.Summary.Summary;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService implements QuizGenerator, SummaryGenerator, PromptProcessor {
    private final ChatClient chatClient;

    public GeminiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Quiz generateQuiz(String topic, Difficulty difficulty) {
        String prompt = "Gere exercícios de dificuldade {difficulty} sobre o " +
                "tópico {topic}, a resposta deve estar estruturada com as respostas, as questões, e a explicação de " +
                "cada alternativa" +
                "(da alternativa estar correta ou incorreta) para cada questão";

        return this.chatClient.prompt()
                .user(u -> u.text(prompt).param("topic", topic).param("difficulty", difficulty)).call().entity(Quiz.class);
    }

    @Override
    public Summary generateSummary(String topic, String size) {
        String prompt = """
                Você é um especialista em criar resumos detalhados e úteis para estudantes. Seu objetivo é condensar informações cruciais sobre um tópico, tornando-o fácil de entender e usar como guia de estudo.
                Por favor, gere um resumo detalhado sobre o tópico: derivadas.
                O resumo deve incluir os seguintes elementos, quando aplicável ao tópico:
                1.  Conceito Fundamental: Uma explicação clara e concisa do que é o tópico.
                2.  Aplicações e Importância: Onde esse conceito é aplicado e por que é importante estudá-lo. Forneça exemplos práticos, se possível.
                3.  Principais Fórmulas e Regras: Liste e explique as fórmulas, teoremas e regras essenciais relacionadas ao tópico. Use notação matemática quando apropriado e explique o significado de cada componente da fórmula.
                4.  Conceitos Relacionados: Mencione brevemente outros conceitos que são importantes para entender completamente este tópico e como eles se conectam.
                5.  Passos Chave para a Solução de Problemas: Se aplicável, forneça um guia passo a passo sobre como abordar problemas relacionados a este tópico.
                6.  Exemplos Práticos: Inclua um ou dois exemplos simples que ilustrem a aplicação dos conceitos e fórmulas.
                7.  Pontos Essenciais para Memorizar: Destaque os pontos mais importantes que um estudante deve memorizar para uma prova ou para ter um entendimento sólido do assunto.
                Certifique-se de que a linguagem seja clara, acessível e focada em ajudar um estudante a aprender e revisar o material de forma eficaz.
                O assunto a ser resumido é: {topic}.
                Caso seja possível/conveniente, sintetize esse guia para o tamanho: {size}
                não use caracteres que possam dar problemas para um parsing, como '*'
                """;

        return this.chatClient.prompt()
                .user(u -> u.text(prompt).param("size", size).param("topic", topic))
                .call()
                .entity(Summary.class);
    }

    @Override
    public PromptResponse getPromptResponse(String prompt) {
        return this.chatClient.prompt().user(u -> u.text(prompt)).call().entity(PromptResponse.class);
    }
}

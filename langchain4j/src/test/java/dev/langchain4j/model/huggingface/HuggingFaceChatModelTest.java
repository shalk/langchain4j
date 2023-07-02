package dev.langchain4j.model.huggingface;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Result;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HuggingFaceChatModelTest {

    @Test
    public void testWhenNullAccessToken() {
        assertThrows(IllegalArgumentException.class, () ->
                HuggingFaceChatModel.builder()
                        .accessToken(null)
                        .modelId("gpt2")
                        .build());
    }

    @Test
    public void testWhenEmptyAccessToken() {
        assertThrows(IllegalArgumentException.class, () ->
                HuggingFaceChatModel.builder()
                        .accessToken("")
                        .modelId("gpt2")
                        .build());
    }

    @Test
    public void testSendUserMessageString() {
        HuggingFaceChatModel model = HuggingFaceChatModel.builder()
                .accessToken(System.getenv("HF_API_KEY"))
                .modelId("tiiuae/falcon-7b-instruct")
                .timeout(Duration.ofSeconds(60))
                .temperature(0.7)
                .maxNewTokens(20)
                .waitForModel(true)
                .build();

        Result<AiMessage> result = model.sendMessages(
                systemMessage("You are a good friend of mine, who likes to answer with jokes"),
                userMessage("Hey Bro, what are you doing?")
        );

        assertThat(result.get().text()).isNotBlank();
        System.out.println(result.get().text());
    }
}
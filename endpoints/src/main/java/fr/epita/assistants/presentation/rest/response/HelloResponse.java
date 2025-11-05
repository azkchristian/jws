package fr.epita.assistants.presentation.rest.response;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Setter
@Getter
@Value
public class HelloResponse
{
    String content;

    public HelloResponse(String content) {
        this.content = content;
    }

    public HelloResponse() {
        this.content = null;
    }
}

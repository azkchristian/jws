package fr.epita.assistants.presentation.rest.request;

import lombok.*;

@AllArgsConstructor
public class ReverseRequest
{
    public String content;

    public ReverseRequest()
    {
        this.content = "";
    }
}

package fr.epita.assistants.presentation.rest.response;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class ReverseResponse
{
    String original;
    String reversed;

    public ReverseResponse(String original, String reversed)
    {
        this.original = original;
        this.reversed = reversed;
    }
    public ReverseResponse()
    {
        this.original = null;
        this.reversed = null;
    }

}

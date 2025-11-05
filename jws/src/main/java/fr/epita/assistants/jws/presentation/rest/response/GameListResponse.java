package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.utils.GameState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GameListResponse {
    public long id;
    public int players;
    public String state;
}


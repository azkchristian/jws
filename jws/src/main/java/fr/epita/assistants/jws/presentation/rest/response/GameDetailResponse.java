package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.utils.GameState;
import fr.epita.assistants.jws.utils.Player;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GameDetailResponse
{
    public LocalDateTime startTime;
    public GameState state;
    // classe de type ""response"" qui contient la list public
    public List<Player> players;
    public List <String> map;
    public long id;
}

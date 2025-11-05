package fr.epita.assistants.jws.data.model;

import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.utils.GameState;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game")

public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public LocalDateTime starttime;
    public GameState state;

    @OneToMany(cascade = CascadeType.ALL)
   // @JoinColumn(name = "game_id")
    public List<PlayerModel> players;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_map", joinColumns = @JoinColumn(name = "gamemodel_id"))
    public List<String>maps;
   // @OneToMany(cascade = CascadeType.ALL)
   // @JoinColumn(name = "gamemodel_id" , referencedColumnName = "id")
   // @JoinColumn(name = "players_id" , referencedColumnName = "id")
   // List<PlayerModel> game_player;

}

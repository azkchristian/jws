package fr.epita.assistants.jws.data.model;

import lombok.With;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name ="player")
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public Timestamp lastbomb;
    public Timestamp lastmovement;
    public int lives;
    public String name;
    public int posx;
    public int posy;
    public int position;

    public long gameId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id" , referencedColumnName = "id")
    public GameModel gameModel;


}

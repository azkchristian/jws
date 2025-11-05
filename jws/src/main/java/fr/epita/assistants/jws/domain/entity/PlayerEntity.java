package fr.epita.assistants.jws.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerEntity {
    public long id;
    public String name;
    public int lives;
    public int posX;
    public int posY;
}

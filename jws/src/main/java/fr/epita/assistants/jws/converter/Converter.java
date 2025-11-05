package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import fr.epita.assistants.jws.utils.Player;

import java.util.ArrayList;
import java.util.List;

public class Converter
{
   //To Entity
    public static GameEntity Gentity(GameModel gamemodel)
    {
        GameEntity res = new GameEntity();
        res.id = gamemodel.id;
        res.players = new ArrayList<>();
        for(int i = 0; i < gamemodel.players.size(); i++)
        {
            res.players.add(Converter.Pentity(gamemodel.players.get(i)));
        }
        res.startime = gamemodel.starttime;
        res.state = gamemodel.state;
        res.map =  gamemodel.maps;

        return res;
    }

    public static PlayerEntity Pentity(PlayerModel param)
    {
        PlayerEntity res = new PlayerEntity();
        res.id = param.id;
        res.lives = param.lives;
        res.posX= param.posx;
        res.posY = param.posy;
        res.name = param.name;
        return  res;
    }

    //to Model
    public static PlayerModel Pmodel(PlayerEntity param)
    {
        PlayerModel res = new PlayerModel();
        //res.id = param.id;
        res.lives = param.lives;
        res.posx= param.posX;
        res.posy = param.posY;
        res.name = param.name;
        return  res;
    }
    public static  GameModel Gmodel (GameEntity gameentity)
    {
        GameModel res = new GameModel();
        //res.id = gameentity.id;
        res.players = new ArrayList<>();
        for(int i = 0; i < gameentity.players.size(); i++)
        {
            res.players.add(Converter.Pmodel(gameentity.players.get(i)));
        }
        res.starttime = gameentity.startime;
        res.state = gameentity.state;
        res.maps =  gameentity.map;;
        return res;
    }


    public static GameListResponse GLresponse(GameEntity gameentity)
    {
        GameListResponse res = new GameListResponse();
        res.id = gameentity.id;
        res.players = gameentity.players.size();
        res.state=gameentity.state.toString();
        return res;
    }

    public static Player PlaytoEnt(PlayerEntity param)
    {
        Player res = new Player();
        res.id = param.id;
        res.lives = param.lives;
        res.posX= param.posX;
        res.posY = param.posY;
        res.name = param.name;
        return  res;
    }
    public static List<Player> Plist (List<PlayerEntity> param)
    {
        List<Player> res = new ArrayList<>();
        for (int i = 0; i < param.size(); i++)
        {
            res.add(PlaytoEnt(param.get(i)));
        }
        return res;
    }

    public static GameDetailResponse GDresponse(GameEntity gameentity)
    {
        GameDetailResponse res = new GameDetailResponse();
        res.startTime = gameentity.startime;
        res.state = gameentity.state;
        res.players = Plist(gameentity.players);
        res.id = gameentity.id;
        res.map = gameentity.getMap();
        return res;
    }

}


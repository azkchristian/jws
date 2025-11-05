package fr.epita.assistants.jws.domain.service;
import java.lang.Character.*;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.converter.Converter;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import fr.epita.assistants.jws.utils.GameState;
import io.quarkus.deployment.annotations.ProduceWeak;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class Service
{
    @Inject
    GameRepository gameRepository;


    @Inject
    PlayerRepository playerRepository;
    @ConfigProperty(name="JWS_MAP_PATH", defaultValue = "src/test/resources/map1.rle") String mapPath;
    public List<GameEntity> getListEntity()
    {
        List<GameModel> gameModels = gameRepository.listAll();
        List<GameEntity> glist = new ArrayList<>();
        if(gameModels.size() == 0)
        {
            return new ArrayList<>();
        }
        for (int i = 0; i < gameModels.size(); i++)
        {
            GameEntity res = Converter.Gentity(gameModels.get(i));
            glist.add(res);
        }
        return glist;
    }
    public boolean isGame(long id)
    {
        if (gameRepository.findById(id) == null)
            return false;
        return true;
    }

    public List<String> fillMap(String path)
    {
        List<String>map = new ArrayList<>();
        try {
            BufferedReader bufferedReader  = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                map.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
    @Transactional
    public GameEntity createGame(String playerName)
    {
        GameModel newGameModel = new GameModel();
        PlayerModel newPlayerModel = new PlayerModel();
        newPlayerModel.posy = 1;
        newPlayerModel.posx = 1;
        newPlayerModel.lives = 3;
        newPlayerModel.name = playerName;
        newPlayerModel.gameModel = newGameModel;
        newGameModel.state = GameState.STARTING;
        newGameModel.maps = fillMap(mapPath);
        newGameModel.players = new ArrayList<>();
        newGameModel.players.add(newPlayerModel);
        newGameModel.starttime = LocalDateTime.now();

        gameRepository.persist(newGameModel);
        playerRepository.persist(newPlayerModel);

        GameEntity newGameEntity =  Converter.Gentity(newGameModel);
        PlayerEntity newPlayerEntity = Converter.Pentity(newPlayerModel);
        newGameEntity.startime = LocalDateTime.now();
        newPlayerEntity.name = playerName;
        newGameEntity.players = new ArrayList<>();
        newGameEntity.players.add(newPlayerEntity);
        newGameEntity.map = newGameModel.maps;
       // newGameEntity.map.forEach(l -> System.out.println(l));


        return newGameEntity;
    }
    public GameEntity getGame(long gameId)
    {
        GameEntity res = Converter.Gentity(gameRepository.findById(gameId));
        return res;
    }
    public GameModel getGameModel(long gameId)
    {
        GameModel res = gameRepository.findById(gameId);
        return res;
    }

    @Transactional
    public GameEntity startGame(long gameId)
    {
        GameModel gameModel = getGameModel(gameId);
        if (gameModel.state == GameState.FINISHED )
            return null;
        gameModel.state = GameState.RUNNING;
        if (gameModel.players.size() == 1)
            gameModel.state = GameState.FINISHED;
        gameRepository.persist(gameModel);
        GameEntity gameEntity = Converter.Gentity(gameModel);
        return gameEntity;
    }

    @Transactional
    public GameEntity joinGame(long gameId, String name)
    {
        boolean res = false;
        GameModel gameModel = getGameModel(gameId);
        if (gameModel == null)
        {
            return null;
        }
        PlayerModel playerModel =  new PlayerModel();
        playerModel.gameId = gameId;
        playerModel.name = name;
        playerModel.lives = 3;
        if (gameModel.players.size() == 1)
        {
            playerModel.posx = 15;
            playerModel.posy = 1;
            res = true;
        }
        if (gameModel.players.size() == 2)
        {
            playerModel.posx = 15;
            playerModel.posy = 13;
            res = true;
        }
        if (gameModel.players.size() == 3)
        {
            playerModel.posx = 1;
            playerModel.posy = 13;
            res = true;
        }
        if (gameModel.players.size()>=4 || gameModel.state == GameState.RUNNING || gameModel.state == GameState.FINISHED)
            return null;

        gameModel.players.add(playerModel);
        gameRepository.persist(gameModel);
        playerRepository.persist(playerModel);
        GameEntity gameEntity = Converter.Gentity(gameModel);
        gameEntity.id = gameId;
        PlayerEntity playerEntity = Converter.Pentity(playerModel);
        gameEntity.startime = LocalDateTime.now();
        playerEntity.name = name;
        gameEntity.map = gameModel.maps;
        return gameEntity;
    }
    public static List<String> decodeMap(List<String> maps)
    {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++)
        {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < maps.get(i).length();j+=2)
            {

                for (int k = 0; k < maps.get(i).charAt(j) - '0'; k+=1)
                {
                    line.append(maps.get(i).charAt(j +1));
                }

            }
            res.add(line.toString());

        }
        //System.out.println(res);
        return res;
    }
    public GameEntity please(long playerId) {

        PlayerModel playerModel = playerRepository.findById(playerId);
        if (playerModel == null)
        {
            return null;
        }
        return new GameEntity();
    }
    @Transactional
    public GameEntity movePlayer(long gameId,long playerId,int posX,int posY)
    {
        GameModel gameModel = getGameModel(gameId);
        PlayerModel playerModel = playerRepository.findById(playerId);
        if (playerModel.lives == 0)
            return null;
        if (gameModel.state == GameState.STARTING || gameModel.state == GameState.FINISHED)
            return null;
        if (playerModel.posx - posX > 1 || playerModel.posx - posX < -1)
        {
            return null;
        }
        if (playerModel.posy - posY > 1 || playerModel.posy - posY < -1)
        {
            return null;
        }
        List<String>newMap = decodeMap(gameModel.maps);
        if (newMap.get(posY).charAt(posX) != 'G')
        {
            return null;
        }
        else {
            playerModel.posx = posX;
            playerModel.posy = posY;
            playerRepository.persist(playerModel);
            gameRepository.persist(gameModel);
            GameEntity res = Converter.Gentity(gameModel);
            return res;
        }
    }
    @Transactional
    public GameEntity plantBomb(long gameId,long playerId,int posX,int posY)
    {
        GameModel gameModel = getGameModel(gameId);
        PlayerModel playerModel = playerRepository.findById(playerId);
        if (playerModel.lives == 0)
            return null;
        if (gameModel.state == GameState.STARTING || gameModel.state == GameState.FINISHED)
            return null;
        if (playerModel.posx - posX > 1 || playerModel.posx - posX < -1)
        {
            return null;
        }
        if (playerModel.posy - posY > 1 || playerModel.posy - posY < -1)
        {
            return null;
        }
        List<String>newMap = decodeMap(gameModel.maps);
        if (newMap.get(posY).charAt(posX) != 'G')
        {
            return null;
        }
        return null;
    }


}

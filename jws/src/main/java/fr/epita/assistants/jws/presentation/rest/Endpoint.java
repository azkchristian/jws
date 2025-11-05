package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.converter.Converter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.service.Service;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.request.MovePlayerRequest;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Endpoint {
    @Inject
    Service service;

    @GET
    @Path("games")
    public Response getGames() {
        Response res;
        List<GameEntity> list = service.getListEntity();
        if (service == null || list.size() == 0) {
            return Response.ok(new ArrayList<>()).status(200).build();
        }
        List<GameListResponse> listResponses = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GameListResponse gres = Converter.GLresponse(list.get(i));
            listResponses.add(gres);
        }
        return Response.ok(listResponses).status(200).build();
    }

    @POST
    @Path("games")
    public Response createGame(@RequestBody CreateGameRequest request) {
        Response res;
        GameEntity gameEntity = service.createGame(request.name);
        if (service == null || request==null|| request.name==null) {
            return Response.status(400).build();
        }
        GameDetailResponse gres = Converter.GDresponse(gameEntity);
        return Response.ok(gres).status(200).build();
    }

    @GET
    @Path("games/{gameId}")
    public Response gameInfo(@PathParam("gameId") long gameId) {
        if (!service.isGame(gameId))
            return Response.status(404).build();
        GameEntity ent = service.getGame(gameId);
        GameDetailResponse gres = Converter.GDresponse(ent);
        return Response.ok(gres).status(200).build();
    }

    @POST
    @Path("games/{gameId}")
    public Response joinGame(@PathParam("gameId") long gameId, @RequestBody CreateGameRequest request) {

        if (!service.isGame(gameId))
        {
            return Response.status(404).build();
        }
        GameEntity ent = service.joinGame(gameId, request.name);
        if (ent != null) {

            GameDetailResponse gres = Converter.GDresponse(ent);
            return Response.ok(gres).status(200).build();
        }
        return Response.status(400).build();

    }


    @PATCH
    @Path("games/{gameId}/start")
    public Response startGame(@PathParam("gameId")long gameId)
    {
        GameEntity ent= service.startGame(gameId);
        if (ent != null)
        {
            GameDetailResponse gres = Converter.GDresponse(ent);
            return Response.ok(gres).status(200).build();
        }
        return  Response.status(404).build();
    }
    @POST
    @Path("games/{gameId}/players/{playerId}/move")
    public Response movePlayer(@PathParam("gameId") long gameId,@PathParam("playerId") long playerId, @RequestBody MovePlayerRequest request)
    {

        GameModel ent= service.getGameModel(gameId);
        if (ent == null || service.please(playerId) ==null)
        {
           return Response.status(404).build();
        }
        else
        {
            GameEntity gres = service.movePlayer(gameId, playerId, request.posX, request.posY);
            if (gres == null) {
                return Response.status(400).build();
            }
            return Response.ok(gres).status(200).build();
        }
    }

    @POST
    @Path("games/{gameId}/players/{playerId}/bomb")
    public Response bomb(@PathParam("gameId") long gameId,@PathParam("playerId") long playerId, @RequestBody MovePlayerRequest request)
    {
        GameModel ent= service.getGameModel(gameId);
        if (ent == null || service.please(playerId) ==null)
        {
            return Response.status(404).build();
        }
        else
        {
            GameEntity gres = service.plantBomb(gameId, playerId, request.posX, request.posY);
            if (gres == null) {
                return Response.status(400).build();
            }
            return Response.ok(gres).status(200).build();
        }
    }

}


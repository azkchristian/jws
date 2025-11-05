package fr.epita.assistants.presentation.rest;

import fr.epita.assistants.presentation.rest.request.ReverseRequest;
import fr.epita.assistants.presentation.rest.response.HelloResponse;
import fr.epita.assistants.presentation.rest.response.ReverseResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Endpoints
{
    @Path("/hello/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@PathParam("name") String name)
    {
        HelloResponse res = new HelloResponse("hello "+name);
        return Response.ok(res).build();
    }
    @Path("/reverse")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rev(ReverseRequest req)
    {
        if (req == null || req.content.isEmpty())
            return Response.status(400).build();
        StringBuilder str = new StringBuilder(req.content);
        str.reverse();
        String reversed = str.toString();
        ReverseResponse res = new ReverseResponse(req.content, reversed);
        return Response.ok(res).build();
    }

}



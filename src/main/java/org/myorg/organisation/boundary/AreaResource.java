package org.myorg.organisation.boundary;

import org.myorg.organisation.domain.service.AreaManager;
import org.myorg.organisation.domain.Area;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The Area REST resource implementation.
 */
@Path("areas")
@RequestScoped
public class AreaResource {

    @Inject
    private AreaManager areaManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response area() {
        return Response.ok(areaManager.findAll()).build();
    }

    @POST
    public Response create(Area area) {
        areaManager.create(area);
        URI location = UriBuilder.fromResource(AreaResource.class)
                .path("/{id}")
                .resolveTemplate("id", area.getId())
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@PathParam("id") Integer id) {
        return Response.ok(areaManager.findByIdArea(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Area area) {
        areaManager.update(id, area);
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", "Area successfully updated");
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        areaManager.delete(id);
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", "Area successfully deleted");
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

}

package org.myorg.organisation.boundary;

import org.myorg.organisation.domain.service.PersonnelManager;
import org.myorg.organisation.domain.Personnel;

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
 * The Personnel REST resource implementation.
 */
@Path("personnel")
@RequestScoped
public class PersonnelResource {

    @Inject
    private PersonnelManager personnelManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response personnel() {
        return Response.ok(personnelManager.findAll()).build();
    }

    @POST
    public Response create(Personnel personnel) {
        personnelManager.create(personnel);
        URI location = UriBuilder.fromResource(PersonnelResource.class)
                .path("/{id}")
                .resolveTemplate("id", personnel.getId())
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@PathParam("id") Integer id) {
        return Response.ok(personnelManager.findByIdPersonnel(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Personnel personnel) {
        personnelManager.update(id, personnel);
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", "Personnel successfully updated");
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        personnelManager.delete(id);
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", "Personnel successfully deleted");
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }
}

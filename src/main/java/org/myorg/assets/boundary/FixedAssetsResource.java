/*
 * This class is the ReST implementation for the Asset. 
 */
package org.myorg.assets.boundary;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.myorg.assets.domain.service.FixedAssetsManager;
import org.myorg.assets.domain.FixedAsset;

/**
 * The FixedAssets REST implementation
 * 
 * @author jnamla
 */

@Path("fixed-assets")
@RequestScoped
public class FixedAssetsResource {
    
    @Inject
    private FixedAssetsManager fixedAssetsHolder;
    
    @Context
    private ResourceContext context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response assets() {
        Collection<FixedAsset> assets = fixedAssetsHolder.findAll();
        return Response.ok(assets).build();
    }
    
    @GET
    @Path("/type/{typeId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getByType(@PathParam("typeId") Integer typeId) {
        Collection<FixedAsset> assets = fixedAssetsHolder.findByAssetTypeId(typeId);
        return Response.ok(assets).build();
    }
    
    @GET
    @Path("/purchase-date/{purchaseDate}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getPurchaseDate(@PathParam("purchaseDate") String purchaseDate) {
        Collection<FixedAsset> assets = fixedAssetsHolder.findByPurchaseDate(purchaseDate);
        return Response.ok(assets).build();
    }
    
    @GET
    @Path("/serial-number/{serialNumber}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getBySerialNumber(@PathParam("serialNumber") String serialNumber) {
        Collection<FixedAsset> assets = fixedAssetsHolder.findBySerialNumber(serialNumber);
        return Response.ok(assets).build();
    }
    
    @Path("/areas")
    public FixedAssetsAreasResource getAreas() {
        FixedAssetsAreasResource fixedAssetsAreasResource = context.getResource(FixedAssetsAreasResource.class);
        return fixedAssetsAreasResource;
    }
    
    @Path("/personnel")
    public FixedAssetsPersonnelResource getPersonnel() {
        FixedAssetsPersonnelResource fixedAssetsPersonnelResource = context.getResource(FixedAssetsPersonnelResource.class);
        return fixedAssetsPersonnelResource;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(FixedAsset asset) {
        fixedAssetsHolder.create(asset);
        URI location = UriBuilder.fromResource(FixedAssetsResource.class)
                .path("/{id}")
                .resolveTemplate("id", asset.getId())
                .build();
        return Response.created(location).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, FixedAsset asset) {
        fixedAssetsHolder.update(id, asset);
        
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", "Fixed Asset successfully updated");
            
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

}

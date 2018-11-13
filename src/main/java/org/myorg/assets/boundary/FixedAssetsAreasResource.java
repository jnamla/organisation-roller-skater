/*
 *  Sub REST resource implementation for areas. 
 */
package org.myorg.assets.boundary;

import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.myorg.assets.domain.service.FixedAssetsManager;
import org.myorg.organisation.domain.Area;

/**
 * The FixedAssets-areas REST implementation
 * 
 * @author jnamla
 */

@RequestScoped
public class FixedAssetsAreasResource {
    
    @Inject
    private FixedAssetsManager fixedAssetsManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response areas() {
        Collection<Area> areas = fixedAssetsManager.findAllAreas();
        return Response.ok(areas).build();
    }

}

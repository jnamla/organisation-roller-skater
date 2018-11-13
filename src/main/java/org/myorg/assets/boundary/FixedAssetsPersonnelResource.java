/*
 * Sub REST resource implementation for personnel. 
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
import org.myorg.organisation.domain.Personnel;

/**
 * The FixedAssets-Personnel REST implementation
 * 
 * @author jnamla
 */

@RequestScoped
public class FixedAssetsPersonnelResource {
    
    @Inject
    private FixedAssetsManager fixedAssetsManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response areas() {
        Collection<Personnel> personnel = fixedAssetsManager.findAllPersonnel();
        return Response.ok(personnel).build();
    }

}


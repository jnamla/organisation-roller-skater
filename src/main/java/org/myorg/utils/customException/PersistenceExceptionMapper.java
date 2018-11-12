package org.myorg.utils.customException;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *  An {@link ExceptionMapper} implementation for all JPA {@link PersistenceException}
 * 
 * @author jnamla
 */
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException>{
    
    @Override
    public Response toResponse(PersistenceException exception) {
        
        Map<String, String> response = new HashMap<>();
            response.put("Code", "PERSISTENCE-ERR-GENERAL");
            response.put("Type", "DATABASE");
            response.put("Message", exception.getMessage());
            
        if(exception instanceof EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(response).type(MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response).type(MediaType.APPLICATION_JSON).build();
        }
    }
}

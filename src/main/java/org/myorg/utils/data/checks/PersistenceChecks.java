package org.myorg.utils.data.checks;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

/**
 * Keeps common checks to be made pre/post db operations and throws exceptions when needed
 * 
 * @author jnamla
 */

public class PersistenceChecks {
    
    public static <T> void checkNotFoundStatus (T result, Class<T> resource){
        if (Objects.isNull(result)) {
            throw new EntityNotFoundException(resource + ": Could not find any.");
        }
    }
    
    public static <T> void checkNotFoundStatus(Collection<T> result, Class<T> resource){
        if (result.isEmpty()) {
            throw new EntityNotFoundException(resource + ": Could not find any.");
        }
    }
    
    public static <T> void isIdConsistentForUpdate(boolean consistent, Class<T> resource){
        if (!consistent) {
            throw new BadRequestException(resource + ": Identifiers are inconsistent. Can't update.");
        }
    }
    
    public static <T> void isValidCheck(boolean valid, String message){
        if (!valid) {
            throw new InternalServerErrorException(message);
        }
    }
}

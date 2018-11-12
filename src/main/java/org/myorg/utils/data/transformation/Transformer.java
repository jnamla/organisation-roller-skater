package org.myorg.utils.data.transformation;

import java.io.InputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 *  Defines common methods needed to transform data
 * 
 * @author jnamla
 */
public class Transformer {

    /**
     * Returns an object of the specified class from a read json.
     *
     * @param <T>
     * @param in InputStream from which the json is read
     * @param theClass the class in which the read json will be transformed
     * @return the area
     */
    
    public static <T> T jsonToObjetFromInputStream(InputStream in, Class<T> theClass) {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(in, theClass);
    }
}

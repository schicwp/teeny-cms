package org.schicwp.dinky.model.type.fields;

import org.schicwp.dinky.model.Content;
import org.schicwp.dinky.model.ContentMap;
import org.schicwp.dinky.model.type.FieldType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * Created by will.schick on 1/5/19.
 */
@Component
public class IntFieldType implements FieldType {


    @Override
    public String getName() {
        return "Int";
    }


    @Override
    public boolean validateSubmission(Object object, ContentMap properties, Collection<String> errors) {
        if (object == null)
            return true;

        if (!Integer.class.isAssignableFrom(object.getClass())) {
            errors.add("Should be integer");
            return false;
        }

        int value = (Integer) object;

        int min = properties.getAsOrDefault("min", Integer.MIN_VALUE);
        int max = properties.getAsOrDefault("max", Integer.MAX_VALUE);

        if (value > max) {
            errors.add("value above max");
            return false;
        }

        if (value < min) {
            errors.add("value below min");
            return false;
        }

        return true;
    }

    @Override
    public Object convertSubmission(Object input, ContentMap properties, Content content) {
        return input;
    }

}

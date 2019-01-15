package org.schicwp.model.type;

import org.schicwp.model.Content;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by will.schick on 1/5/19.
 */
public class Field {

    private final FieldType fieldType;

    private final boolean required;

    private final Map<String,Object> properites;

    private final String name;

    private final boolean indexed;

    public Field(FieldType fieldType, boolean required, Map<String, Object> properites, String name, boolean indexed) {
        this.fieldType = fieldType;
        this.required = required;
        this.properites = properites;
        this.name = name;
        this.indexed = indexed;
    }

    public boolean isRequired() {
        return required;
    }

    public String getName() {
        return name;
    }

    public boolean validateSubmission(Object object){


        return fieldType.validateSubmission(object,properites, new ArrayList<>());
    }

    public boolean isIndexed() {
        return indexed;
    }

    public Object convertSubmission(Object object, Map<String, Object> properites, Content content){
        return fieldType.convertSubmission(object,properites,content);
    }
}

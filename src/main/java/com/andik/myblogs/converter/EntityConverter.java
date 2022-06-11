/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author andik
 */
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {
    
    private final static Map<Object, String> entities = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (Map.Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        synchronized (entities) {
            if (!entities.containsKey(value)) {
                String id = UUID.randomUUID().toString();
                entities.put(value, id);
                return id;
            } else {
                return entities.get(value);
            }
        }
    }
    
    
    
}

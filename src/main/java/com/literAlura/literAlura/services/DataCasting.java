package com.literAlura.literAlura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataCasting implements IDataCasting {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String data, Class<T> myClass) {
        try{
            return this.objectMapper.readValue(data, myClass);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}

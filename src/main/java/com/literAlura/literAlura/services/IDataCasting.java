package com.literAlura.literAlura.services;

public interface IDataCasting {
    <T> T getData(String data, Class<T> myClass);
}

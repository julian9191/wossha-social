package com.wossha.social.infrastructure.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import java.util.ArrayList;

public class MapperDozer {

    protected static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    static {
        ArrayList<String> mappingfileUrls = new ArrayList<>();
        mappingfileUrls.add("dozer/dozerClothingMapping.xml");
        ((DozerBeanMapper) mapper).setMappingFiles(mappingfileUrls);
    }

    public <T> T map(Object source, Class<T> destinationClass){
        return mapper.map(source, destinationClass);
    }
}

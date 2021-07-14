package com.business.search.ddl.service;

import com.business.search.ddl.model.IndexMapping;

import java.io.IOException;

public interface MappingService {
    boolean createMapping(IndexMapping indexMapping) throws IOException;
}

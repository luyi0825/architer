package com.business.search.ddl.service;

import com.business.search.ddl.model.SearchMapping;

import java.io.IOException;

public interface MappingService {
    public boolean createMapping(SearchMapping searchMapping) throws IOException;
}

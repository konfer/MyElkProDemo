package com.konfer.es.service.intefaces;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;

public interface SearchIndexService
{
    public SearchResponse getSearchResponse();

    public SearchRequestBuilder getSearchRequestBuilder();
}

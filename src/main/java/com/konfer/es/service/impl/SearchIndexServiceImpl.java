package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import com.konfer.es.service.intefaces.SearchIndexService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;

public class SearchIndexServiceImpl implements SearchIndexService
{
    private TransportClient esConnClient;
    private String index;
    private String indexType;

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getIndexType()
    {
        return indexType;
    }

    public void setIndexType(String indexType)
    {
        this.indexType = indexType;
    }

    public SearchIndexServiceImpl()
    {
        esConnClient = ClusterConn.CLUSTER_CONN.getClientInstance();
    }

    public SearchResponse getSearchResponse()
    {
        return getSearchRequestBuilder().execute().actionGet();
    }

    public SearchRequestBuilder getSearchRequestBuilder()
    {
        //        SearchResponse searchResponse= new SearchResponse();
        //        searchResponse = esConnClient.prepareSearch(index).setTypes(indexType);
        //                .execute().actionGet();
        return esConnClient.prepareSearch(index).setTypes(indexType);
    }



}

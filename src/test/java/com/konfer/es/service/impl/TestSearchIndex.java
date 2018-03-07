package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Test;

public class TestSearchIndex
{
    private TransportClient client = null;
    private SearchIndexServiceImpl searchIndexService = null;
    private QueryBuilders queryBuilders;

    @Before
    public void testConn()
    {
        client = ClusterConn.CLUSTER_CONN.getClientInstance();
        System.out.println("The connect Node num is: " + client.connectedNodes().size());

        searchIndexService=new SearchIndexServiceImpl();
        searchIndexService.setIndex(TestUnitConfiguration.testIndex);
        searchIndexService.setIndexType(TestUnitConfiguration.testIndexType);


    }

    @Test
    public void getIndexNum() throws Exception
    {
        long indexNum=searchIndexService.getSearchResponse().getHits().getTotalHits();
        System.out.println("The num of index is: "+indexNum);
    }

    @Test
    public void testSearchByMatchQuery() throws Exception
    {
        SearchResponse searchResponse=searchIndexService.getSearchRequestBuilder()
                .setQuery(QueryBuilders.matchQuery("name","lin"))
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .get();
        SearchHits hits=searchResponse.getHits();
        long searchIndexNum=hits.getTotalHits();
        System.out.println("found index num is: "+searchIndexNum);

        SearchHit[] searchHits=hits.getHits();
        for (SearchHit searchHit:searchHits)
        {
            System.out.println(searchHit.getSourceAsString());
        }


    }

    @Test
    public void testMultiSearchQuery() throws Exception
    {
        SearchResponse searchResponse=searchIndexService.getSearchRequestBuilder()
                //.setQuery(QueryBuilders.multiMatchQuery("20","name","age"))
                .setQuery(QueryBuilders.queryStringQuery("age:23"))//支持luncence语法
                .get();
        SearchHits hits=searchResponse.getHits();
        long searchIndexNum=hits.getTotalHits();
        System.out.println("multiSearch found index num is: "+searchIndexNum);

        SearchHit[] searchHits=hits.getHits();
        for (SearchHit searchHit:searchHits)
        {
            System.out.println(searchHit.getSourceAsString());
        }

    }




}

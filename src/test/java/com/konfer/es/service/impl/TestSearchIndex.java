package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Before;
import org.junit.Test;

public class TestSearchIndex
{
    private TransportClient client = null;
    private SearchIndexServiceImpl searchIndexService = null;

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



}

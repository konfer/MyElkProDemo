package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import org.elasticsearch.client.transport.TransportClient;

public class TestSearchIndex
{
    private TransportClient client = null;
    private SearchIndexServiceImpl searchIndexService = null;

    public void testConn()
    {
        client = ClusterConn.CLUSTER_CONN.getClientInstance();
        System.out.println("The connect Node num is: " + client.connectedNodes().size());


        searchIndexService=new SearchIndexServiceImpl();

    }


}

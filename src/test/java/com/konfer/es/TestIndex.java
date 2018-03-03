package com.konfer.es;

import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Before;
import org.junit.Test;
import service.impl.IndexServiceImpl;
import service.interfaces.IndexService;

import java.util.HashMap;
import java.util.Map;

public class TestIndex
{
    private TransportClient client = null;

    @Before
    public void testConn() throws Exception
    {
        client = ClusterConn.CLUSTER_CONN.getClientInstance();
        System.out.print("The connect Node num is: " + client.connectedNodes().size());
    }

    @Test
    public void testIndex() throws Exception
    {
        IndexResponse indexResponse = client.prepareIndex(TestUnitConfiguration.testIndex, TestUnitConfiguration
                .testIndexType, "1").setSource().get();
        System.out.println(" version is: " + indexResponse.getVersion());
    }

    @Test
    public void testIndexGet() throws Exception
    {
        IndexServiceImpl service = new IndexServiceImpl();
        service.setIndex(TestUnitConfiguration.testIndex);
        service.setIndexType(TestUnitConfiguration.testIndexType);
        service.getByIndexId("5");
        GetResponse getResponse = service.getGetResponse();
        Map<String, Object> map = getResponse.getSource();
        System.out.println(map);

    }

    @Test
    public void testIndexUpdata() throws Exception
    {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("name", "test");
        hashMap.put("age", "23");
        IndexServiceImpl service = new IndexServiceImpl();
        service.setIndex(TestUnitConfiguration.testIndex);
        service.setIndexType(TestUnitConfiguration.testIndexType);
        service.updataIndexByMap("5", hashMap);
    }


}

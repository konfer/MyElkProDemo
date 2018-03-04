package com.konfer.es;

import ModelBean.Person;
import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.impl.IndexServiceImpl;
import service.interfaces.IndexService;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TestIndex
{
    private TransportClient client = null;
    private IndexServiceImpl service;

    @Before
    public void testConn() throws Exception
    {
        client = ClusterConn.CLUSTER_CONN.getClientInstance();
        System.out.print("The connect Node num is: " + client.connectedNodes().size());

        service = new IndexServiceImpl();
        service.setIndex(TestUnitConfiguration.testIndex);
        service.setIndexType(TestUnitConfiguration.testIndexType);
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

        service.getByIndexId("8");
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

        service.updataIndexByMap("5", hashMap);
    }

    @Test
    public  void testBeanModel() throws Exception
    {
        Person p=new Person();
        p.setAge(21);
        p.setName("zhang");

        String json = "{" +
                "\"name\":\"zhang\"," +
                "\"age\":\"22\"," +
                "}";

        ObjectMapper objectMapper=new ObjectMapper();
        byte[] personJsonType=objectMapper.writeValueAsBytes(p);
        System.out.println(personJsonType);
        service.updataIndexByJsonType("8",personJsonType);
//        client.prepareIndex(TestUnitConfiguration.testIndex, TestUnitConfiguration.testIndexType, "7")
//                .setSource(objectMapper.writeValueAsBytes(p),XContentType.JSON).get();

    }




}

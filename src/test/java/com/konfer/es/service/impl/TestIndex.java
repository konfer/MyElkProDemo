package com.konfer.es.service.impl;

import com.konfer.es.ModelBean.Person;
import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;
import com.konfer.es.service.impl.IndexServiceImpl;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

public class TestIndex
{
    private TransportClient client = null;
    private IndexServiceImpl service;
    private BulkRequestBuilder bulkRequestBuilder;

    @Before
    public void testConn() throws Exception
    {
        client = ClusterConn.CLUSTER_CONN.getClientInstance();
        bulkRequestBuilder=client.prepareBulk();
        System.out.println("The connect Node num is: " + client.connectedNodes().size());

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

        service.getByIndexId("26");
        GetResponse getResponse = service.getGetResponse();
        Map<String, Object> map = getResponse.getSource();
        System.out.println("Searched index infomation is: "+ map);

    }

    @Test
    public void testIndexUpdata() throws Exception
    {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("name", "lin");
        hashMap.put("age", "32");

        service.updataIndexByMap("89", hashMap);
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

    @Test
    public void testContentBuilder() throws Exception
    {
        XContentBuilder endObject= XContentFactory.jsonBuilder().startObject()
                .field("name","wu")
                .field("age",18)
                .endObject();
        service.updataIndexByJsonType("99",endObject);
    }

//    @Test
//    public void updateIndex() throws Exception
//    {
//        XContentBuilder builder=XContentFactory.jsonBuilder().startObject().field("age",34).endObject();
//        service.partialUpdataIndex("13",builder);
//    }

    @Test
    public void deleteIndex() throws Exception
    {
        service.delIndex("13");
    }


    @Test
    public void multiUpdate() throws Exception
    {
        XContentBuilder builderOne=XContentFactory.jsonBuilder().startObject()
                .field("name","chen")
                .field("age",25)
                .endObject();
        XContentBuilder builderTwo=XContentFactory.jsonBuilder().startObject()
                .field("name","li")
                .field("age",27)
                .endObject();
        XContentBuilder builderThree=XContentFactory.jsonBuilder().startObject()
                .field("name","你好中国")
                .field("age",30)
                .endObject();
        XContentBuilder builderFour=XContentFactory.jsonBuilder().startObject()
                .field("name","你好")
                .field("age",31)
                .endObject();
        XContentBuilder builderFive=XContentFactory.jsonBuilder().startObject()
                .field("name","中国")
                .field("age",32)
                .endObject();


        bulkRequestBuilder.add(service.updateRequest("19",builderOne));
        bulkRequestBuilder.add(service.updateRequest("20",builderTwo));
        bulkRequestBuilder.add(service.updateRequest("24",builderThree));
        bulkRequestBuilder.add(service.updateRequest("25",builderFour));
        bulkRequestBuilder.add(service.updateRequest("26",builderFive));

        BulkResponse bulkItemResponses=bulkRequestBuilder.get();

        if (bulkItemResponses.hasFailures())
        {
            System.out.println("Bulk Update Fault");
        }

    };

}

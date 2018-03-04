package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import com.konfer.es.service.intefaces.IndexService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Map;

public class IndexServiceImpl implements IndexService
{
    private TransportClient esConnClient;
    private String index;
    private String indexType;
    private IndexResponse indexResponse;

    public GetResponse getGetResponse()
    {
        return getResponse;
    }

    private GetResponse getResponse;
    //private

    public IndexServiceImpl()
    {
        esConnClient = ClusterConn.CLUSTER_CONN.getClientInstance();
    }

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

    public void index(String id)
    {
        // indexResponse=esConnClient.prepareIndex(testIndex,testType,id).;
    }

    public SearchResponse getIndexCount()
    {
        SearchResponse searchResponse= new SearchResponse();
        searchResponse = esConnClient.prepareSearch(index).setTypes(indexType).execute().actionGet();
        return searchResponse;
    }

    public void getByIndexId(String indexId)
    {
        getResponse = esConnClient.prepareGet(index, indexType, indexId).execute().actionGet();

    }

    public Map<String, Object> getMapByIndexId(String indexId)
    {
        return null;
    }

    public DeleteResponse delIndex(String id)
    {
        DeleteResponse deleteResponse=esConnClient.prepareDelete(index,indexType,id).get();
        return deleteResponse;
    }

    public UpdateResponse partialUpdataIndex(String id, XContentBuilder jsonType)
    {
        UpdateResponse updateResponse=new UpdateResponse();
        updateResponse=esConnClient.prepareUpdate(index, indexType, id).setDoc(jsonType).get();
        return updateResponse;
    }

    public IndexResponse updataIndexByMap(String id, Map map)
    {
        IndexResponse indexResponse=new IndexResponse();
        indexResponse=esConnClient.prepareIndex(index, indexType, id).setSource(map).get();
        return indexResponse;
    }

    public IndexResponse updataIndexByJsonType(String id, byte[] jsonType)
    {
        IndexResponse indexResponse=new IndexResponse();
        indexResponse=esConnClient.prepareIndex(index, indexType, id).setSource(jsonType, XContentType.JSON).get();
        return indexResponse;
    }

    public IndexResponse updataIndexByJsonType(String id, XContentBuilder jsonType)
    {
        IndexResponse indexResponse=new IndexResponse();
        indexResponse=esConnClient.prepareIndex(index, indexType, id).setSource(jsonType).get();
        return indexResponse;
    }


    public void multiGet(String... ids)
    {

    }

    public IndexRequestBuilder updateRequest(String id, XContentBuilder jsonType)
    {
        return esConnClient.prepareIndex(index, indexType, id).setSource(jsonType);
    }
}

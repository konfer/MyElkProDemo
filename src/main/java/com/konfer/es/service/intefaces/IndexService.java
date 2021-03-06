package com.konfer.es.service.intefaces;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Map;

public interface IndexService
{
    public void index(String id);





    public Map<String, Object> getMapByIndexId(String indexId);

    public DeleteResponse delIndex(String id);

    public UpdateResponse partialUpdataIndex(String id, XContentBuilder jsonType);

    public IndexResponse updataIndexByMap(String id, Map map);

    public IndexResponse updataIndexByJsonType(String id, byte[] jsonType);

    public IndexResponse updataIndexByJsonType(String id, XContentBuilder jsonType);

    public void multiGet(String... ids);

    public IndexRequestBuilder updateRequest(String id, XContentBuilder jsonType);

}

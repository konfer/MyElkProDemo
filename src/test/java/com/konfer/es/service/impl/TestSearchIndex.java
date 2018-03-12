package com.konfer.es.service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import Util.Conn.EsClusterConn.TestUnitConfiguration;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.similarity.ScriptedSimilarity;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortOrder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
               // .setQuery(QueryBuilders.queryStringQuery("age:23"))//支持luncence语法
           // .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name","zhang"))
                // .must(QueryBuilders.matchQuery("age","21")))
               // .setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name","test").boost(8.0f))
                //        .should(QueryBuilders.matchQuery("age","21")))
               // .setQuery(QueryBuilders.termQuery("name","li"))
                .setQuery(QueryBuilders.matchQuery("name","你好中国")
                        .operator(Operator.AND)
                )
               // .addSort("age",SortOrder.ASC)
               .setPostFilter(QueryBuilders.rangeQuery("age").from(5).to(35))
                .get();
        SearchHits hits=searchResponse.getHits();
        long searchIndexNum=hits.getTotalHits();
        System.out.println("multiSearch found index num is: "+searchIndexNum);

        SearchHit[] searchHits=hits.getHits();
        for (SearchHit searchHit:searchHits)
        {
            System.out.println("testMultiSearchQuery result: "+searchHit.getSourceAsString());
        }

    }

    @Test
    public void testAggregation() throws Exception
    {
        SearchResponse searchResponse=searchIndexService.getSearchRequestBuilder()
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("term_age").field("age").size(5))
               // .setFrom(4)
                .get();
        Terms terms=searchResponse.getAggregations().get("term_age");
        List<? extends Bucket> buckets=terms.getBuckets();
        System.out.println("aggregation num is: "+buckets.size());
        for (Bucket bucket:buckets)
        {
            System.out.println(bucket.getKey()+"--"+bucket.getDocCount());
        }
    }

    @Test
    public void testAggregationSum() throws Exception
    {
        searchIndexService=new SearchIndexServiceImpl();
        searchIndexService.setIndex(TestUnitConfiguration.testIndex);
        searchIndexService.setIndexType(TestUnitConfiguration.testIndexType);

        SearchResponse searchResponse=searchIndexService.getSearchRequestBuilder()
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("term_name").field("name.keyword")
                        .subAggregation(AggregationBuilders.sum("age_sum").field("age"))
                        .size(Integer.MAX_VALUE))
                .get();
        Terms terms=searchResponse.getAggregations().get("term_name");
        List<? extends Bucket> buckets=terms.getBuckets();
        for (Bucket bucket:buckets)
        {
            Sum sum=bucket.getAggregations().get("age_sum");
            System.out.println(bucket.getKey()+"--"+sum.getValue());
        }
    }


}

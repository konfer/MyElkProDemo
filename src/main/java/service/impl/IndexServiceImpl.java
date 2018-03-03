package service.impl;

import Util.Conn.EsClusterConn.ClusterConn;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import service.interfaces.IndexService;

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

    public void getByIndexId(String indexId)
    {
        getResponse = esConnClient.prepareGet(index, indexType, indexId).execute().actionGet();

    }

    public Map<String, Object> getMapByIndexId(String indexId)
    {
        return null;
    }

    public void delIndex(String id)
    {

    }

    public void updataIndex(String id)
    {

    }

    public void updataIndexByMap(String id, Map map)
    {
        esConnClient.prepareIndex(index, indexType, id).setSource(map).get();
    }

    public void multiGet(String... ids)
    {

    }
}

package service.interfaces;

import org.elasticsearch.action.index.IndexResponse;

import java.util.Map;

public interface IndexService
{
    public void index(String id);

    public void getByIndexId(String indexId);

    public Map<String, Object> getMapByIndexId(String indexId);

    public void delIndex(String id);

    public void updataIndex(String id);

    public IndexResponse updataIndexByMap(String id, Map map);

    public IndexResponse updataIndexByJsonType(String id, byte[] jsonType);

    public void multiGet(String... ids);

}

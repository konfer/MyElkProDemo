package service.interfaces;

import java.util.Map;

public interface IndexService
{
    public void index(String id);

    public void getByIndexId(String indexId);

    public Map<String, Object> getMapByIndexId(String indexId);

    public void delIndex(String id);

    public void updataIndex(String id);

    public void updataIndexByMap(String id, Map map);

    public void multiGet(String... ids);

}

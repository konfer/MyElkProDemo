package Util.Conn.EsClusterConn;

import Util.Conn.EsProHandler.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.elasticsearch.client.transport.TransportClient;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public enum ClusterConn
{
    CLUSTER_CONN;

    private Logger logger = LogManager.getLogger(ClusterConn.class);
    private TransportClient clientInstance;

    private ClusterConn()
    {
        try
        {
            logger.info("get it");
            clientInstance = new PreBuiltTransportClient(ConnConfigration.settings).addTransportAddresses
                    (ConnConfigration.getTransportAddress_ClusterHostOne(), ConnConfigration.getTransportAddress_ClusterHostTwo()
                            , ConnConfigration.getTransportAddress_ClusterHostThree());
        } catch (Exception e)
        {
            ExceptionHandler.ExceptionHandler("ClusterConn", e);
            logger.error("HadoopMasterNodeConni's except:  " + e);
        }
    }

    public TransportClient getClientInstance()
    {
        return clientInstance;
    }

    public void ExceptionHandler(Object obj, Exception e)
    {

    }

}

package Util.Conn.EsClusterConn;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnConfigration
{
    public static Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name",
            "my-application").build();

    private static TransportAddress transportAddress_ClusterHostOne;
    private static TransportAddress transportAddress_ClusterHostTwo;
    private static TransportAddress transportAddress_hadoopSlaveThree;

    public static TransportAddress getTransportAddress_ClusterHostOne()
    {
        try
        {
            transportAddress_ClusterHostOne = new TransportAddress(InetAddress.getByName("172.16.235.143"), 9300);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return transportAddress_ClusterHostOne;
    }

    public static TransportAddress getTransportAddress_ClusterHostTwo()
    {
        try
        {
            transportAddress_ClusterHostTwo = new TransportAddress(InetAddress.getByName("172.16.235.141"), 9300);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return transportAddress_ClusterHostTwo;
    }

    public static TransportAddress getTransportAddress_ClusterHostThree()
    {
        try
        {
            transportAddress_hadoopSlaveThree = new TransportAddress(InetAddress.getByName("172.16.235.142"), 9300);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return transportAddress_hadoopSlaveThree;
    }

}

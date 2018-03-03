package com.konfer.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class TestEs
{
    @Test
    public void testCom() throws NamedXContentRegistry.UnknownNamedObjectException, UnknownHostException, Exception
    {
        Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name",
                "my-application").build();
        TransportClient client = null;
        // client.transport.ignore_cluster_name  //设置 true ，忽略连接节点集群名验证
        //client.transport.ping_timeout       //ping一个节点的响应时间 默认5秒
        // client.transport.nodes_sampler_interval //sample/ping 节点的时间间隔，默认是5s
        // Settings settings = Settings.builder().put("cluster.name", "my-application").build();
        TransportAddress transportAddress_hadoopMaster = new TransportAddress(InetAddress.getByName("172.16.235.143")
                , 9300);
        TransportAddress transportAddress_hadoopSlaveOne = new TransportAddress(InetAddress.getByName
                ("172.16.235.141"), 9300);
        //TransportAddress transportAddress_hadoopSlaveTwo = new TransportAddress(InetAddress.getByName
        // ("192.168.63.128"), 9300);

        client = new PreBuiltTransportClient(settings).addTransportAddresses(transportAddress_hadoopMaster,
                transportAddress_hadoopSlaveOne);
        System.out.print(client.connectedNodes().size());

        List<DiscoveryNode> connectNodes = client.connectedNodes();
        for (DiscoveryNode conNode : connectNodes)
        {
            System.out.print(" node id: " + conNode.getHostAddress());
        }

    }

}

package com.konfer.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;

public class TestEsConClusterName
{
    @Test
    public void testCon() throws NamedXContentRegistry.UnknownNamedObjectException, Exception
    {
        Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name",
                "my-application").build();
        TransportAddress transportAddress_hadoopMaster = new TransportAddress(InetAddress.getByName("172.16.235.200")
                , 9300);
        TransportAddress transportAddress_hadoopSlaveOne = new TransportAddress(InetAddress.getByName
                ("172.16.235.201"), 9300);

        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses
                (transportAddress_hadoopMaster, transportAddress_hadoopSlaveOne);
        System.out.print(client);

        List<DiscoveryNode> connectNodes = client.connectedNodes();
        for (DiscoveryNode conNode : connectNodes)
        {
            System.out.print(conNode.getHostAddress());
        }
    }


}

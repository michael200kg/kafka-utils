import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.Node;

/**
 * @author Mikhail_Vershkov
 */

public class SimpleDescriberApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "localhost:9092");
        AdminClient adminClient = AdminClient.create(kafkaProperties);
        DescribeClusterResult describeClusterResult = adminClient.describeCluster();
        Collection<Node> brokerDetails = describeClusterResult.nodes().get();
        System.out.println("host and port details");
        for(Node broker:brokerDetails) {
            System.out.println(broker.toString());
        }
    }
}

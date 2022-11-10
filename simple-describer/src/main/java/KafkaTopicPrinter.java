import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mikhail_Vershkov
 */

public class KafkaTopicPrinter {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaTopicPrinter.class);

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("client.id", "java-admin-client");

        try (AdminClient client = AdminClient.create(properties)) {

            System.out.println("***** Brokers *****");
            printBrokerDetails(client);
            System.out.println("");

            System.out.println("***** Consumer groups *****");
            printConsumerGroupsDetails(client);
            System.out.println("");

            System.out.println("***** Topics *****");
            printTopicDetails(client);
            System.out.println("");

            System.out.println("***** Topics Description *****");
            printTopicDescription(client);
        }
    }

    private static void printBrokerDetails(AdminClient adminClient) throws ExecutionException, InterruptedException {
        DescribeClusterResult describeClusterResult = adminClient.describeCluster();
        Collection<Node> brokerDetails = describeClusterResult.nodes().get();
        System.out.println("host and port details");
        for(Node broker : brokerDetails) {
            System.out.println(broker.toString());
        }
    }

    private static void printConsumerGroupsDetails(AdminClient client) throws ExecutionException, InterruptedException {

        System.out.println("Valid groups:");
        DescribeConsumerGroupsResult consumerGroups = client.describeConsumerGroups(client.listConsumerGroups().valid().get().stream()
                .map(ConsumerGroupListing::groupId)
                .collect(Collectors.toList()));
        consumerGroups.all().get().forEach((key,value) -> System.out.println( key + ":" + value.toString()));
        System.out.println();
        System.out.println("Failed:");
        client.listConsumerGroups().errors().get().forEach(System.out::println);

    }

    private static void printTopicDetails(AdminClient client) throws ExecutionException, InterruptedException {
        Collection<TopicListing> listings;
        listings = getTopicListing(client, true);
        listings.forEach(topic -> System.out.println("Name: " + topic.name() + ", isInternal: " + topic.isInternal()));
    }

    private static void printTopicDescription(AdminClient client) throws ExecutionException, InterruptedException {
        Collection<TopicListing> listings;
        listings = getTopicListing(client, false);
        List<String> topics = listings.stream().map(TopicListing::name).collect(Collectors.toList());
        DescribeTopicsResult result = client.describeTopics(topics);
        result.values().forEach((key, value) -> {
            try {
                System.out.println(key + ": " + value.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                LOGGER.info("Failed to execute", e.getCause());
            }
        });

    }

    private static Collection<TopicListing> getTopicListing(AdminClient client, boolean isInternal) throws InterruptedException, ExecutionException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(isInternal);
        return client.listTopics(options).listings().get();
    }

}

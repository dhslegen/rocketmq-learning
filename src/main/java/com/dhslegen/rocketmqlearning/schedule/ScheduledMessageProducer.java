package com.dhslegen.rocketmqlearning.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author dhslegen
 * @date 2019/11/6
 */
public class ScheduledMessageProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", "hhh;jj", "keys;keys01", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

}

package com.dhslegen.rocketmqlearning.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dhslegen
 * @date 2019/11/7
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("example_group_name");
        //Launch the instance.
        producer.start();
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            messages.add(msg);
        }

        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message> listItem = splitter.next();
                SendResult sendResult = producer.send(listItem);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                //handle the error
            }
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}

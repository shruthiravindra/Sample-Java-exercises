package receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import receiver.result.ResultMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;


public class ReceiveResult {

    private static final String RESULT_QUEUE = "resultmessage";

    // Receive Result Part
    public void receiveResult(Channel queueChannel) throws IOException {
        System.out.println("creating queue :" + RESULT_QUEUE);
        // Connecting to the Queue.
        queueChannel.queueDeclare(RESULT_QUEUE, false, false, false, null);
        // Consume from the queue.
        queueChannel.basicConsume(RESULT_QUEUE, true, getDefaultConsumer(queueChannel));
    }


    public DefaultConsumer getDefaultConsumer(Channel queueChannel) {
        // processing the Result.
        return new DefaultConsumer(queueChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                 ResultMessage resultMessage = deserializeMessage(body);
                System.out.println(resultMessage.toString());
             }
        };
    }

    private ResultMessage deserializeMessage(byte[] body) {
        try {
            ByteArrayInputStream b = new ByteArrayInputStream(body);
            ObjectInputStream o = new ObjectInputStream(b);
            return (ResultMessage)o.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

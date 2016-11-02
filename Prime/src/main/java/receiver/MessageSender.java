package receiver;

import com.rabbitmq.client.Channel;
import receiver.result.ResultMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

import static receiver.QueueConfig.createQueueConnection;

// Send Result Class After Processing.
public class MessageSender {

    private static final String RESULT_QUEUE = "resultmessage";

    public void sendMessage(ResultMessage resultMessage) {
        try {
            // Create the connection.
            Channel channel = createQueueConnection();
            // Connect to the queue.
            channel.queueDeclare(RESULT_QUEUE, false, false, false, null);
            // Publish the Result.
            channel.basicPublish("", RESULT_QUEUE, null, serializeMessage(resultMessage));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public byte[] serializeMessage(ResultMessage resultMessage){
        try{
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(resultMessage);
            return b.toByteArray();
        }
         catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}

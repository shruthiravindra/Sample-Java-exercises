package sender;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendNumber {

    private final static String QUEUE_NAME = "randomnumber";

    // Sender Part for Random Number.
    public void sendMessage(Channel queueChannel, int number) throws IOException, TimeoutException {
        // Queue Creation
        queueChannel.queueDeclare(QUEUE_NAME,false,false,false,null );
        String message = Integer.toString(number);
        // Publish
        queueChannel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }
}

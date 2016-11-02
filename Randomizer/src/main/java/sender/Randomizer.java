package sender;

import com.rabbitmq.client.Channel;
import receiver.ReceiveResult;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Randomizer {


    public static void main(String[] args) throws IOException, TimeoutException {
        // create Queue Connection
        Channel queueConnection = QueueConfig.createQueueConnection();

        // Start Subscriber for result:
        new ReceiveResult().receiveResult(queueConnection);

        while(true) {
            // Generate Random Number
            Random random = new Random();
            int randomNumber = random.nextInt(Integer.MAX_VALUE);
            // send the numbers.
            new SendNumber().sendMessage(queueConnection,randomNumber);
        }

    }
}

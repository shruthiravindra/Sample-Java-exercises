package receiver;


import com.rabbitmq.client.*;
import receiver.result.ResultMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import static receiver.QueueConfig.createQueueConnection;

public class MessageReceiver {

    private static final String RECEIVER_QUEUE = "randomnumber";


    public void receiveAndProcessMessage() throws IOException, TimeoutException {
        // Create Queue Connection.
        Channel channel = createQueueConnection();
        DefaultConsumer defaultConsumer = getDefaultConsumer(channel);
        System.out.println("creating queue :" + RECEIVER_QUEUE);
        channel.queueDeclare(RECEIVER_QUEUE, false, false, false, null);
        channel.basicConsume(RECEIVER_QUEUE, true, defaultConsumer);

    }

    private DefaultConsumer getDefaultConsumer(final Channel channel) {
        // Process data received from Randomizer
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                ResultMessage resultMessage = processNumber(body);
                // Send Result.
                new MessageSender().sendMessage(resultMessage);
            }
        };
    }

    private ResultMessage processNumber(byte[] body) throws UnsupportedEncodingException {
        String message = new String(body, "UTF-8");
        // Convert from String to Int.
        int number = Integer.parseInt(message);
        // Check if Number is Prime.
        boolean isPrimeNumber = isPrimeNumber(number);
        // Create the result.
        return new ResultMessage(number, isPrimeNumber);


    }

    private boolean isPrimeNumber(int number) {
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 3; i < sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }




}

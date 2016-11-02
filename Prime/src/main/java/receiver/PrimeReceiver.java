package receiver;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PrimeReceiver {

    public static void main(String[] args) throws IOException, TimeoutException {

        MessageReceiver queueConfig = new MessageReceiver();
        // Create the ramdomNumber Subscriber.
        queueConfig.receiveAndProcessMessage();
    }
}

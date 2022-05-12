package edu.ufp.inf.sd.rmi.froggergame.client;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edu.ufp.inf.sd.rmi.froggergame.util.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MQEmitter {
    /*+ name of the queue */
    //public final static String QUEUE_NAME="hello_queue";

    /**
     * Run publisher Send several times from terminal (will send msg "hello world" to Recv):
     * $ ./runproducer
     *
     * Challenge: concatenate several words from command line args (args[3].. args[n]):
     * $ ./runcnsumer hello world again (will concatenate msg "hello world again" to send)
     *
     * @param args
     */
    public static void main(String[] args) {
        String host=args[0];
        int port=Integer.parseInt(args[1]);

        String exchangeName=args[2];

        /* try-with-resources will close resources automatically in reverse order... avoids finally */
        try (Connection connection= RabbitUtils.newConnection2Server(host, port, "guest", "guest");
             Channel channel=RabbitUtils.createChannel2Server(connection)) {

            System.out.println(" [x] Declare exchange: '" + exchangeName + "' of type " + BuiltinExchangeType.FANOUT.toString());

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

            String message=RabbitUtils.getMessage(args, 3);

            String routingKey="";
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent: '"+ message +"'");
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(MQEmitter.class.getName()).log(Level.INFO, e.toString());
        }
        /* try-with-resources will close resources automatically in reverse order, thus avoiding finally clause.
          finally {
            try {
                // Lastly, we close the channel and the connection
                if (channel != null) { channel.close(); }
                if (connection != null) { connection.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
    }
}

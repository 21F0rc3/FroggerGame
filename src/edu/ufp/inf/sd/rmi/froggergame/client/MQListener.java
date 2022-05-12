package edu.ufp.inf.sd.rmi.froggergame.client;

/**
 * 1. Run the rabbitmq server as a shell process, by calling:
 * $ rabbitmq-server
 *
 * <p>
 * 2. Run consumer Recv that keeps running, waiting for messages from publisher Send (Use Ctrl-C to stop):
 * $ ./runconsumer
 *
 * <p>
 * 3. Run publisher Send several times from terminal (will send mesg "hello world"):
 * $ ./runproducer
 *
 * <p>
 * 4. Check RabbitMQ Broker runtime info (credentials: guest/guest4rabbitmq) from:
 * http://localhost:15672/
 *
 * @author rui
 */

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rmi.froggergame.util.RabbitUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MQListener {
    /**
     * Run consumer Recv that keeps running, waiting for messages from publisher Send (Use Ctrl-C to stop):
     * $ ./runconsumer
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String host=args[0];
            int port=Integer.parseInt(args[1]);

            String exchangeName=args[2];

            Connection connection=RabbitUtils.newConnection2Server(host,port,"guest","guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

            String queueName=channel.queueDeclare().getQueue();

            String routingKey="";
            channel.queueBind(queueName, exchangeName, routingKey);

            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() +": Will create Deliver Callback...");
            System.out.println("[*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] Consumer Tag [" + consumerTag + "] - Received '" + message + "'");
            };
            CancelCallback cancelCallback=(consumerTag) -> {
                System.out.println("[x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
            };
            channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
        } catch (Exception e) {
            //Logger.getLogger(Recv.class.getName()).log(Level.INFO, e.toString());
            e.printStackTrace();
        }
    }
}

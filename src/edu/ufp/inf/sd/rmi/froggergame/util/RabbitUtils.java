package edu.ufp.inf.sd.rmi.froggergame.util;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rmi.froggergame.client.ClientMediator;
import edu.ufp.inf.sd.rmi.froggergame.client.FroggerClient;
import edu.ufp.inf.sd.rmi.froggergame.client.game.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.server.ServerMediator;
import edu.ufp.inf.sd.rmi.froggergame.server.states.FrogMoveEvent;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.server.states.TrafficMoveEvent;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitUtils {
    static String HOST = "localhost";
    static int PORT = 5672;

    /**
     * Create a connection to the rabbitmq server/broker
     * (abstracts the socket connection, protocol version negotiation and authentication, etc.)
     */
    public static Connection newConnection2Server(String host, int port, String username, String passwd) throws IOException, TimeoutException {
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() + "->newConnection2Server(): host=" + host+", port="+port);
        //Create a factory for connection establishment
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        //Use same username/passwd as for accessing Management UI @ http://localhost:15672/
        //Default credentials are: guest/guest (change accordingly)
        factory.setUsername(username);
        factory.setPassword(passwd);

        //Create a channel which offers most of the API methods MAIL_TO_ADDR rabbitmq broker
        Connection connection=factory.newConnection();
        return connection;
    }

    /**
     * Create a channel to the rabbitmq server/broker
     */
    public static Channel createChannel2Server(Connection connection) throws IOException, TimeoutException {
        Channel channel=connection.createChannel();
        return channel;
    }

    /**
     * Selects the routing key from a set of keys
     *
     * @param setOfKeys
     * @param routingKeyIndex
     * @return
     */
    public static String getRouting(String[] setOfKeys, int routingKeyIndex) {
        if (setOfKeys.length < routingKeyIndex) {
            return "anonymous.info";
        }
        return setOfKeys[routingKeyIndex];
    }

    /**
     * Selects the message from a set of messages
     *
     * @param messages
     * @param messageIndex
     * @return
     */
    public static String getMessage(String[] messages, int messageIndex) {
        if (messages.length < messageIndex) {
            return "Hello World!";
        }
        return joinStrings(messages, " ", messageIndex);
    }

    public static void printArgs(String[] args) {
        for (int i=0; i<args.length; i++) {
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() + "->printArgs(): args[" + i + "]="+args[i]);
        }
    }

    /**
     * Concatenates a set of strings, separated by a given delimiter
     *
     * @param strings
     * @param delimiter
     * @param startMsgIndex
     * @return
     */
    public static String joinStrings(String[] strings, String delimiter, int startMsgIndex) {
        int length=strings.length;
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() + "->joinStrings(): strings.length=" + length);

        if (length < startMsgIndex) {
            return "";
        }
        StringBuilder words=new StringBuilder(strings[startMsgIndex]);
        for (int i=startMsgIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() + "->joinStrings(): words = " + words.toString());
        return words.toString();
    }

    public static GameState recreateObject(String message) {
        String[] token = message.split(",");

        ArrayList<Integer> frogsLives = new ArrayList<>();
        for(int i=1; i<=4; i++) {
            frogsLives.add(i-1, Integer.parseInt(token[i]));
        }
        int GameState = Integer.parseInt(token[5]);
        int levelTimer = Integer.parseInt(token[6]);
        int GameLevel = Integer.parseInt(token[7]);
        int playersNumber = Integer.parseInt(token[8]);
        String serverName = token[9];

        switch (token[0]) {
            case "GameState": {
                return new GameState(frogsLives, GameState, levelTimer, GameLevel, playersNumber, serverName);
            }
            case "FrogMoveEvent": {
                Integer frogID = Integer.parseInt(token[10]);
                String direction = token[11];

                return new FrogMoveEvent(frogsLives, GameState, levelTimer, GameLevel, playersNumber, serverName, frogID, direction);
            }
            case "TrafficMoveEvent": {
                String trafficType = token[10];
                Posititon pos = new Posititon(Double.parseDouble(token[11]), Double.parseDouble(token[12]));
                Posititon vel = new Posititon(Double.parseDouble(token[13]), Double.parseDouble(token[14]));
                String spriteName = token[15];
                long deltaMs = Long.parseLong(token[16]);

                return new TrafficMoveEvent(frogsLives, GameState, levelTimer, GameLevel, playersNumber, serverName, trafficType, pos, vel, spriteName, deltaMs);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Envia o game state apenas para o servidor a que o cliente esta conectado
     *
     * @param state Estado do jogo
     *
     * @author Gabriel Fernandes 04/06/2022
     */
    public static void sendGameStateToServer(GameState state) {
        try (Connection connection=RabbitUtils.newConnection2Server(HOST, PORT, "guest", "guest");
             Channel channel=RabbitUtils.createChannel2Server(connection)) {
            // Declare a queue where to send msg (idempotent, i.e., it will only be created if it doesn't exist);
            channel.queueDeclare(state.getServerName()+"ToServer", false, false, false, null);
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            String message=state.toString();

            // Publish a message to the queue (content is byte array encoded with UTF-8)
            channel.basicPublish("", state.getServerName()+"ToServer", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException | TimeoutException e) {
            Logger.getLogger(RabbitUtils.class.getName()).log(Level.INFO, e.toString());
        }
    }

    public static void sendGameStateToClients(GameState state) {
        try {
            // O exchange sera o nome do proprio servidor
            String exchangeName = state.getServerName()+"ToClient";

            Connection connection= RabbitUtils.newConnection2Server(HOST, PORT, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);

            System.out.println(" [x] Declare exchange: '" + exchangeName + "' of type " + BuiltinExchangeType.FANOUT.toString());

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

            String routingKey = "";
            channel.basicPublish(exchangeName, routingKey, null, state.toString().getBytes("UTF-8"));
            System.out.println(" [x] Sent: "+ state.toString());
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, e.toString());
        }

    }

    /**
     * Inicia um listener do RabbitMQ para aquele frogger game receber updates dos utilizadores
     * que depois distribui para os clientes
     *
     * @param serverName Nome do servidor que sera utilizado para criar a queue que ele consome
     *
     * @author Gabriel Fernandes 04/06/2022
     */
    public static void initRabbitMQServerListener(String serverName) {
        try{
            String queueName= serverName + "ToServer";

            // Open a connection and a channel to rabbitmq broker/server
            Connection connection=RabbitUtils.newConnection2Server(HOST, PORT, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);

            //Declare queue from which to consume (declare it also here, because consumer may start before publisher)
            channel.queueDeclare(queueName, false, false, false, null);
            //channel.queueDeclare(Send.QUEUE_NAME, true, false, false, null);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Will create Deliver Callback...");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            boolean run = true;

            //DeliverCallback is an handler callback (lambda method) to consume messages pushed by the sender.
            //Create an handler callback to receive messages from queue
            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message=new String(delivery.getBody(), "UTF-8");
                Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
                System.out.println(" [x] Received '" + message + "'");

                // Recria o objeto e envia para os clientes
                GameState gameState = RabbitUtils.recreateObject(message);
                sendGameStateToClients(gameState);

                while (!run){
                    try {
                        long sleepMillis = 2000;
                        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": sleep " +sleepMillis);
                        Thread.currentThread().sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
            //Associate callback with channel queue
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia um listener do RabbitMQ para o cliente receber updates do servidor
     *
     * @author Gabriel Fernandes 04/06/2022
     */
    public static void initRabbitMQClientListener() {
        Runnable runnable = () -> {
            try {
                String exchangeName = ClientMediator.getInstance().getFroggerGameRI().getServerInfo()[0] + "ToClient";

                Connection connection = RabbitUtils.newConnection2Server(HOST, PORT, "guest", "guest");
                Channel channel = RabbitUtils.createChannel2Server(connection);

                channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

                String queueName = channel.queueDeclare().getQueue();

                String routingKey = "";
                channel.queueBind(queueName, exchangeName, routingKey);

                Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName() + ": Will create Deliver Callback...");
                System.out.println("[*] Waiting for messages. To exit press CTRL+C");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("[x] Consumer Tag [" + consumerTag + "] - Received '" + message + "'");

                    GameState gameState = RabbitUtils.recreateObject(message);
                    gameState.execute();
                };
                CancelCallback cancelCallback = (consumerTag) -> {
                    System.out.println("[x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
                };
                channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}

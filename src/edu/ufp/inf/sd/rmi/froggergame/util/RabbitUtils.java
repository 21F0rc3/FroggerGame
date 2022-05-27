package edu.ufp.inf.sd.rmi.froggergame.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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

        switch (token[0]) {
            case "GameState": {
                return new GameState(frogsLives, GameState, levelTimer, GameLevel);
            }
            case "FrogMoveEvent": {
                Integer frogID = Integer.parseInt(token[8]);
                String direction = token[9];

                return new FrogMoveEvent(frogsLives, GameState, levelTimer, GameLevel, frogID, direction);
            }
            case "TrafficMoveEvent": {
                String trafficType = token[8];
                Posititon pos = new Posititon(Double.parseDouble(token[9]), Double.parseDouble(token[10]));
                Posititon vel = new Posititon(Double.parseDouble(token[11]), Double.parseDouble(token[12]));
                String spriteName = token[13];
                long deltaMs = Long.parseLong(token[14]);

                return new TrafficMoveEvent(frogsLives, GameState, levelTimer, GameLevel, trafficType, pos, vel, spriteName, deltaMs);
            }
            default: {
                return null;
            }
        }
    }
}

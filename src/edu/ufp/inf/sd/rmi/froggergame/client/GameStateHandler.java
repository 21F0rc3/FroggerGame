package edu.ufp.inf.sd.rmi.froggergame.client;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edu.ufp.inf.sd.rmi.froggergame.client.frogger.Main;
import edu.ufp.inf.sd.rmi.froggergame.server.Component;
import edu.ufp.inf.sd.rmi.froggergame.server.states.FrogMoveEvent;
import edu.ufp.inf.sd.rmi.froggergame.server.states.GameState;
import edu.ufp.inf.sd.rmi.froggergame.server.states.TrafficMoveEvent;
import edu.ufp.inf.sd.rmi.froggergame.util.RabbitUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe ira receber os pedidos de eventos GameState
 * e após os processar irá executar os eventos na instnacia do jogo
 *
 * Esta classe funciona como middleware que recebe pedidos e chama funções do jogo
 * que efetua ações
 */
public class GameStateHandler implements Component {
    private Thread thread;
    private Main gameInstance;
    private Integer playerIndex;

    public GameStateHandler() {
        try {
            playerIndex = Integer.parseInt(Mediator.getInstance().getFroggerGameRI().getServerInfo()[2]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Runnable runnable = () -> {
                gameInstance = new Main(playerIndex);
                gameInstance.run();
        };

        thread = new Thread(runnable);
    }

    /**
     * Serve para criar uma nova thread que contem o jogo.
     * Deste modo, o servidor não bloqueia porque o jogo comecou.
     * Caso contrario, o segundo cliente não conseguia abrir um jogo.
     *
     * @author Gabriel Fernandes 13/05/2022
     */
    public void start() {
        thread.start();
    }

    /**
     * Importante para a sincronização de instancias
     *
     * Chama uma função que efetua um movimento no frog especificado
     *
     * @author Gabriel Fernandes 12/05/2022
     */
    public void move(FrogMoveEvent event) {
        // If muito importante !! Acredito que ele comeca a fazer updates antes do jogo comecar então
        // a instancia ainda não esta definida no inicio
        if(gameInstance != null) gameInstance.move(event.getFrogID(), event.getDirection());
    }

    /**
     * Importante para a sincronização de instancias
     *
     * Chama uma função que adiciona um novo item de trafico nas posições indicadas
     *
     * @param event Evento que indica que tipo de item criar e onde
     *
     * @uthor Gabriel Fernandes 12/05/2022
     */
    public void movingTraffic(TrafficMoveEvent event) {
        // If muito importante !! Acredito que ele comeca a fazer updates antes do jogo comecar então
        // a instancia ainda não esta definida no inicio
        if (gameInstance != null)
            gameInstance.movingTraffic(event.getTrafficType(), event.getPos(), event.getVel(), event.getSpriteName(), event.getDeltaMs());
    }

    public void sendGameState(GameState state) {
        if(FroggerClient.SYNC_METHOD == "RMI") {
            sendGameStateRMI(state);
        }
        if(FroggerClient.SYNC_METHOD == "RabbitMQ") {
            sendGameStateMQ(state);
        }
    }

    private void sendGameStateMQ(GameState state) {
        try {
            // O exchange sera o nome do proprio servidor
            String exchangeName = Mediator.getInstance().getFroggerGameRI().getServerInfo()[0];

            Connection connection= RabbitUtils.newConnection2Server(FroggerClient.HOST, FroggerClient.PORT, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);

            System.out.println(" [x] Declare exchange: '" + exchangeName + "' of type " + BuiltinExchangeType.FANOUT.toString());

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

            String routingKey = "";
            channel.basicPublish(exchangeName, routingKey, null, state.toString().getBytes("UTF-8"));
            System.out.println(" [x] Sent:");
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, e.toString());
        }
    }

    private void sendGameStateRMI(GameState state) {
        try {
            Mediator.getInstance().getFroggerGameRI().setGameState(state);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() throws RemoteException {
        return "GameStateHandler";
    }
}

package edu.ufp.inf.sd.rmi.froggergame.util;

public class Sync {
    /**
     * Define qual tecnologia utiliza para fazer a sincronização entre as instancias
     *
     * RabbitMQ - Utiliza o Publish/Subscribe do RabbitMQ
     * RMI - Utiliza o Observer do RMI
     */
    public static String SYNC_METHOD = "RabbitMQ";

    public static String RABBITMQ = "RabbitMQ";
    public static String RMI = "RMI";
}

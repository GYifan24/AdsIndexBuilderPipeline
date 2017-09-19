package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Main {

    private final static String IN_QUEUE_NAME = "q_product";
    private static ObjectMapper mapper;

    private static IndexBuilder indexBuilder;

    private static String memcachedServer = "127.0.0.1";
    private static int memcachedPortal = 11211;
    private static String mysql_host = "127.0.0.1:3306";
    private static String mysql_db = "searchads";
    private static String mysql_user = "root";
    private static String mysql_pass = "cs504";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        mapper = new ObjectMapper();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel inChannel = connection.createChannel();
        inChannel.basicQos(10); // Per consumer limit
        inChannel.queueDeclare(IN_QUEUE_NAME, true, false, false, null);

        indexBuilder = new IndexBuilder(memcachedServer, memcachedPortal, mysql_host, mysql_db, mysql_user, mysql_pass);

        Consumer consumer = new DefaultConsumer(inChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    String message = new String(body, "UTF-8");
                    Ad ad = mapper.readValue(message, Ad.class);
                    indexBuilder.buildInvertIndex(ad);
                    indexBuilder.buildForwardIndex(ad);

                }
                catch (Exception e){

                }

            }
        };

        inChannel.basicConsume(IN_QUEUE_NAME, true, consumer);

    }
}

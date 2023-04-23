package com.example.demo.hello;

import java.util.UUID;
import java.util.logging.Logger;

import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.hello.entity.Book;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.ejb.ActivationConfigProperty;

/**
 * <p>
 * A simple Message Driven Bean that asynchronously receives and processes the
 * messages that are sent to the queue.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 */
@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/HELLOWORLDMDBQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "5") })
public class HelloWorldQueueMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldQueueMDB.class.toString());

    private static final String TO_QUEUE_1 = "to.queue.1";
    private static final String TO_QUEUE_2 = "to.queue.2";

    @Inject
    @JMSConnectionFactory("java:jboss/RemoteJmsXA")
    private JMSContext context;

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        try {
            String body = rcvMessage.getBody(String.class);
            LOGGER.info("Received Message from queue: " + body);
            em.persist(new Book(UUID.randomUUID().toString(), "received msg"));
            if (body.equalsIgnoreCase("ex1")) {
                throw new JMSException("test1");
            }
            context.createProducer().send(context.createQueue(TO_QUEUE_1), "jms 2.0 rocks " + body);
            em.persist(new Book(UUID.randomUUID().toString(), "sent to queue 1"));
            if (body.equalsIgnoreCase("ex2")) {
                throw new JMSException("test2");
            }
            context.createProducer().send(context.createQueue(TO_QUEUE_2), "jms 2.0 rocks " + body);
            em.persist(new Book(UUID.randomUUID().toString(), "sent to queue 2"));
            if (body.equalsIgnoreCase("ex3")) {
                throw new JMSException("test3");
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

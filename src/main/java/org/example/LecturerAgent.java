package org.example;

// THESE ARE THE MISSING IMPORTS
import java.util.List;
import java.util.Arrays;

// JADE IMPORTS
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class LecturerAgent extends Agent {
    // This acts as the Lecturer's personal calendar
    private List<String> busySlots = Arrays.asList("Monday 09:00", "Wednesday 14:00");

    @Override
    protected void setup() {
        System.out.println("Lecturer Agent [" + getLocalName() + "] is online and checking their diary...");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Listen specifically for Call For Proposals (CFP)
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage msg = receive(mt);

                if (msg != null) {
                    String requestedTime = msg.getContent();
                    ACLMessage reply = msg.createReply();

                    // LOGIC: Check if the slot is in the busy list
                    if (busySlots.contains(requestedTime)) {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("Denied: I am already teaching at " + requestedTime);
                        System.out.println(getLocalName() + " refused slot: " + requestedTime);
                    } else {
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent("Available");
                        System.out.println(getLocalName() + " proposed slot: " + requestedTime);
                    }
                    send(reply);
                } else {
                    block(); // Wait for new messages
                }
            }
        });
    }
}
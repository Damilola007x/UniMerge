package org.example;

// MISSING IMPORT: You must add this!
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class NegotiationBehavior extends SimpleBehaviour {

    // The constructor must take an Agent as a parameter
    public NegotiationBehavior(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        // Your logic here
    }

    @Override
    public boolean done() {
        return false;
    }
}
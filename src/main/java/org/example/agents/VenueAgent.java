package org.example.agents;

import jade.core.Agent;
import org.example.repository.ScheduleRepository;

public class VenueAgent extends Agent {
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            ScheduleRepository repo = (ScheduleRepository) args[0];
            addBehaviour(new VenueResponseBehavior(repo));
        }
    }
}



package org.example.agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class StudentAgent extends Agent {
    private String courseCode;
    private String preferredDay;
    private String preferredVenue;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length >= 3) {
            this.courseCode = (String) args[0];
            this.preferredDay = (String) args[1];
            this.preferredVenue = (String) args[2];

            System.out.println("StudentAgent " + getLocalName() + " spawned for " + courseCode);
            addBehaviour(new NegotiationInitiator());
        } else {
            System.out.println("StudentAgent failed: Missing arguments (Course, Day, Venue)");
            doDelete();
        }
    }

    private class NegotiationInitiator extends OneShotBehaviour {
        @Override
        public void action() {
            // 1. Create the CFP (Call For Proposal)
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

            // Find the VenueAgent (Assumed name is 'VenueAgent')
            cfp.addReceiver(new jade.core.AID("VenueAgent", jade.core.AID.ISLOCALNAME));

            // 2. CRITICAL: Format the content as COURSE:DAY:VENUE
            // This matches the parsing logic in VenueResponseBehavior
            String combinedContent = courseCode + ":" + preferredDay + ":" + preferredVenue;
            cfp.setContent(combinedContent);

            System.out.println(getLocalName() + " sending CFP with content: " + combinedContent);
            myAgent.send(cfp);

            // 3. Wait for the Verdict (PROPOSE or REFUSE)
            MessageTemplate mt = MessageTemplate.MatchInReplyTo(cfp.getReplyWith());
            ACLMessage reply = myAgent.blockingReceive(10000); // 10 second timeout

            if (reply != null) {
                if (reply.getPerformative() == ACLMessage.PROPOSE) {
                    System.out.println("Negotiation Successful: " + reply.getContent());

                    // Send Acceptance to finalize
                    ACLMessage accept = reply.createReply();
                    accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    accept.setContent("Accepting assigned slot.");
                    myAgent.send(accept);
                } else if (reply.getPerformative() == ACLMessage.REFUSE) {
                    System.out.println("Negotiation Refused: " + reply.getContent());
                }
            } else {
                System.out.println("Negotiation Failed: No response from VenueAgent");
            }
        }
    }
}
package org.example.agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.example.repository.ScheduleRepository;
import org.example.controller.ScheduleController;

public class VenueResponseBehavior extends CyclicBehaviour {
    private final ScheduleRepository scheduleRepo;

    public VenueResponseBehavior(ScheduleRepository repo) {
        this.scheduleRepo = repo;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            // Expected format from StudentAgent: "COURSE:DAY:VENUE"
            String content = msg.getContent();
            String[] parts = content.split(":");

            // Safety Check: If the message format is incomplete, refuse it
            if (parts.length < 3) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("Error: Message format must be COURSE:DAY:VENUE");
                myAgent.send(reply);
                return;
            }

            String courseCode = parts[0].trim();
            String requestedDay = parts[1].trim();
            String requestedVenue = parts[2].trim();

            ACLMessage reply = msg.createReply();

            // 1. Fetch live constraints from the Lecturer's Controller
            String allowedVenue = ScheduleController.globalVenue != null ? ScheduleController.globalVenue.trim() : "";
            String blockedDays = ScheduleController.globalBlacklist != null ? ScheduleController.globalBlacklist : "";

            // 2. Perform Strict Validation
            boolean alreadyExists = scheduleRepo.existsByCourseCode(courseCode);
            boolean isDayBlocked = blockedDays.contains(requestedDay);
            // This is the core fix: It checks if student's venue matches lecturer's venue
            boolean isVenueAuthorized = requestedVenue.equalsIgnoreCase(allowedVenue);

            if (alreadyExists) {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("REFUSE: " + courseCode + " is already scheduled in the database.");
            }
            else if (isDayBlocked) {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("REFUSE: " + requestedDay + " has been blacklisted by the Lecturer.");
            }
            else if (!isVenueAuthorized) {
                // REJECT if the student picked a venue that wasn't broadcasted by the lecturer
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("REFUSE: Unauthorized Venue. Per Lecturer policy, you must use: " + allowedVenue);
            }
            else {
                // SUCCESS: All agent constraints met
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent("Settlement: Slot assigned for " + courseCode + " at " + allowedVenue + " on " + requestedDay);
            }

            myAgent.send(reply);
            System.out.println("VenueAgent Verdict: " + reply.getContent());
        } else {
            block();
        }
    }
}
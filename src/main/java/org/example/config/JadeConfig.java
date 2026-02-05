package org.example.config;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import org.example.repository.ScheduleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JadeConfig {
    @Bean
    public AgentContainer jadeContainer() {
        try {
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            p.setParameter(Profile.GUI, "false");
            p.setParameter("services", "jade.core.messaging.TopicManagementService");
            return rt.createMainContainer(p);
        } catch (Exception e) { return null; }
    }
}
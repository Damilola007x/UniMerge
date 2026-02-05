package org.example;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class JadeRuntimeService {

    private ContainerController mainContainer;

    @PostConstruct
    public void initJade() {
        // Explicitly call jade.core.Runtime to avoid confusion with java.lang.Runtime
        jade.core.Runtime rt = jade.core.Runtime.instance();

        // Create a profile for the main container
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true"); // Set to true to see the JADE window

        this.mainContainer = rt.createMainContainer(p);
        System.out.println("JADE Runtime started successfully.");
    }

    public ContainerController getContainer() {
        return mainContainer;
    }
}
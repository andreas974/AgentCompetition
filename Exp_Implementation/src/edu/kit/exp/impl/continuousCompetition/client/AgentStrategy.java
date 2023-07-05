package edu.kit.exp.impl.continuousCompetition.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by dschnurr on 18.03.15.
 */

public class AgentStrategy {

    private static final Logger log4j = LogManager.getLogger(AgentStrategy.class.getName());
    private final AgentCore agentCore;

    private int myRole;

    public AgentStrategy(AgentCore agentCore) {
        log4j.info("AgentStrategy(): {}", agentCore);
        this.agentCore = agentCore;
        log4j.info("this.AgentInterface = {}", this.agentCore);
}

    void init(ContinuousCompetitionParamObject initialMarketUpdate) {
        log4j.info("Initialize information based on received initial market update.");

        // Optional: read and process information conveyed by the initial marketUpdate
        // For example: read role code
        myRole = initialMarketUpdate.getRoleCode();

        // Todo: set inital action for the start of the simulation
        // For example: set action 25 as the initial action
        int initAction = 25;

        // Initial action is sent to server
        agentCore.updateAction(initAction);
        log4j.info("Set inital action to {}", initAction);
    }

    // Market updaets are received every 500ms
    void processMarketUpdate(ContinuousCompetitionParamObject marketUpdate) {
        log4j.info("Received marketUpdate (id: {})", marketUpdate.getCountId());

        // Todo: implement information processing
        // countId identifies the marketUpdate and is incremented for every new marketUpdate
        int countId = marketUpdate.getCountId();
        log4j.info("(int) countID: {}", countId);

        // read average market action
        double averageMarketAction = marketUpdate.getaMarket();
        log4j.info("(double) pMarket: {}", averageMarketAction);
        log4j.info("(int) pMarket: {}",(int) averageMarketAction);

        // Store actions of all four firms in array
        int[] actions = new int[4];
        actions[0] = marketUpdate.getaFirmA();
        actions[1] = marketUpdate.getaFirmB();
        actions[2] = marketUpdate.getaFirmC();
        actions[3] = marketUpdate.getaFirmD();
        // The current own action can then be retrieved as follows:
        int myLastAction = actions[myRole];
        log4j.info("Own action in last round: {}", myLastAction);

        int newAction = myLastAction;
        // You may want to set a new action based on the received information, e.g. the average market price
        newAction = (int) averageMarketAction;

        // Alternatively you may want to set a random action every 10 seconds (10 * 2 * 500ms updates => countid = 20)
        // if (countId % 20 == 0) {
        //     newAction = setNewRandomAction();
        //}

        // Send updated action to server
        agentCore.updateAction(newAction);

        log4j.info("Updated action to {}", newAction);
    }

    int setNewRandomAction() {

        // Todo: implement action update based on internal processing (e.g. learning algorithm)
        // Simple example: set a random action between 0 and 100:
        Random rand = new Random();
        int randomAction = rand.nextInt(101);

        log4j.info("Created new random action {}", randomAction);
        return randomAction;
    }
}

package com.hydrogennx;

import com.hydrogennx.javafx.ActionPhase;
import com.hydrogennx.javafx.ScreenFramework;

import java.util.List;

/**
 * An abstract class. This holds all relevant information about the current game.
 * This class is never used directly -- instead, a LocalPracticeInstance, ClientManager or HostManager should be created.
 */
public abstract class GameInstance {

    GameManager gameManager;
    GameState gameState;

    public GameInstance(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //TODO create a ClientManager and a HostManager when we are ready to begin netcode.

    public abstract void queueAttack(List<AttackSequence> attackSequences);

    public void updateScreen() {
        switch (gameState) {
            case TURN:
                gameManager.screenFramework.wcm.setScreen(ScreenFramework.TURN_PHASE_ID);
                break;
            case ACTION:
                gameManager.screenFramework.wcm.setScreen(ScreenFramework.ACTION_PHASE_ID);
                break;
            default:
                return; //should not happen
        }
    }

    protected void changeGameState(GameState gameState) {
        this.gameState = gameState;
        gameManager.updateScreen();
    }

    public void update(double time) {

        if (gameState == GameState.ACTION) {

            //TODO get and update the ActionPhase.
            ActionPhase actionPhase = (ActionPhase) gameManager.screenFramework.wcm.getController(ScreenFramework.ACTION_PHASE_ID);

            actionPhase.update(time);

        }

    }

    public abstract void endAttack();
}

package com.hydrogennx.core;

import com.hydrogennx.controller.ActionPhase;
import com.hydrogennx.core.attack.AttackSequence;
import com.hydrogennx.core.javafx.ScreenFramework;
import com.hydrogennx.controller.TurnPhase;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Manages the bit of the game we care about.
 * It moves the information about the game state from the TurnPhase to the ActionPhase and back again.
 */
public class LocalPracticeInstance extends GameInstance {

    Player mainPlayer;

    public LocalPracticeInstance(GameManager gameManager) {
        super(gameManager);

        mainPlayer = new Player(PlayerColor.BLUE, "Practice");

        mainPlayer.addEnemy(mainPlayer); //you are your own worst enemy, because you are your only enemy

        allPlayers.add(mainPlayer);

        mainPlayer.setStartingMana(1000);

        gameState = GameState.TURN;

    }

    @Override
    public void queueAttack(Player attacker, List<AttackSequence> attackSequences) {


        int manaCost = AttackSequence.getCost(attackSequences);
        System.out.println(manaCost);
        System.out.println(attacker.getMana());

        if (manaCost > attacker.getMana()) {
            return;
        }

        attacker.registerAttack(manaCost, this);

        changeGameState(GameState.ACTION);

        ActionPhase actionPhase = (ActionPhase) gameManager.getWindowController(ScreenFramework.ACTION_PHASE_ID);
        actionPhase.addAttackSequences(attackSequences);
    }

    @Override
    public void recallAttack(Player attacker) {
        //nothing
    }

    @Override
    public int getManaReturn() {

        return turn + 200;

    }


    @Override
    public void endAttack() {
        changeGameState(GameState.TURN);

        TurnPhase turnPhase = (TurnPhase) gameManager.getWindowController(ScreenFramework.TURN_PHASE_ID);
        turnPhase.updateState();

    }

    @Override
    public void registerDefeat() {

        super.registerDefeat();

        changeGameState(GameState.GAME_OVER);

    }

    @Override
    public void endGame() {

        super.endGame();

        gameManager.stopGame();

        changeGameState(GameState.YET_TO_BEGIN);

    }

    @Override
    public Player getCurrentPlayer() {
        return mainPlayer;
    }

    @Override
    public void addPlayer(Player player) {
        //nothing
    }


}

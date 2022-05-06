package com.phonepe.interview.models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<Player> battingOrder = new ArrayList<>();
    private Integer nextBatsmanId = 0;
    private final Score score = new Score(0, 0);

    public Team (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addPlayerInBattingOrder(Player player) {
        battingOrder.add(player);
    }

    public Player sendNextBatsman() {
        try {
            Player player = battingOrder.get(nextBatsmanId++);
            player.sendInToBat();
            return player;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Score getTeamScore() {
        return this.score;
    }

    public void printScoreCard() {
        for (Player p:  this.battingOrder) {
            p.printScoreDetails();
        }
    }

    public void dismissPlayer(Player player) {
        player.dismiss();
        this.score.setWickets(this.score.getWickets() + 1);
    }

    public void updateScore(Integer runs, Player player) {
        this.score.setRuns(this.score.getRuns() + runs);
        player.updatePlayerScore(runs);
    }
}

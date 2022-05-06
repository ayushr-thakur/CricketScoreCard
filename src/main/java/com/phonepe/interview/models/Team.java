package com.phonepe.interview.models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<Player> battingOrder = new ArrayList<>();
    private Integer nextBatsmanId = 0;
    private Integer extras = 0;
    private Integer ballsCount = 0;
    private final TeamScore score = new TeamScore(0, 0);

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

    public TeamScore getTeamScore() {
        return this.score;
    }

    public void printScoreCard() {
        System.out.printf("Scorecard for %s:%n", this.name);
        System.out.printf("%s\t\t%s\t\t%s\t\t%s\t\t%s%n", "Player Name", "Score", "4s", "6s", "Balls");
        for (Player p:  this.battingOrder) {
            p.printScoreDetails();
        }
        System.out.printf("Extras: %d%n", this.extras);
        this.score.printScore();
        System.out.printf("Overs: %s%n", getOverCount());
        System.out.println();
    }

    private String getOverCount() {
        Integer overs = ballsCount / 6;
        Integer balls = ballsCount % 6;
        return balls.equals(0) ? String.format("%d", overs) : String.format("%d.%d", overs, balls);
    }

    public void dismissPlayer(Player player) {
        player.dismiss();
        this.ballsCount += 1;
        this.score.setWickets(this.score.getWickets() + 1);
    }

    public void incrementExtras(Integer extra) {
        this.extras += extra;
        this.score.setRuns(this.score.getRuns() + extra);
    }

    public void updateScore(Integer runs, Player player) {
        this.score.setRuns(this.score.getRuns() + runs);
        this.ballsCount += 1;
        player.updatePlayerScore(runs);
    }
}

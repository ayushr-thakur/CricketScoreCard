package com.phonepe.interview.models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<Player> battingOrder = new ArrayList<>();
    private Integer nextBatsmanId = 0;
    private Integer extras = 0;
    private Integer totalScore = 0;
    private Integer ballsCount = 0;
    private Integer wicketsFallen = 0;

    public Team (String name) {
        this.name = name;
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

    public Integer getTotalScore() {
        return this.totalScore;
    }

    public Integer getWicketsFallen() {
        return this.wicketsFallen;
    }

    public void addPlayerInBattingOrder(Player player) {
        battingOrder.add(player);
    }

    public void printScoreCard() {
        System.out.println(String.format("Scorecard for %s:", this.name));
        System.out.println(String.format("%s\t\t%s\t%s\t%s\t%s", "Player Name", "Score", "4s", "6s", "Balls"));
        for (Player p:  this.battingOrder) {
            p.printScoreDetails();
        }
        System.out.println(String.format("Extras: %d", this.extras));
        System.out.println(String.format("Total: %d/%d", this.totalScore, this.wicketsFallen));
        Integer overs = ballsCount / 6;
        Integer balls = ballsCount % 6;
        String overCount = balls.equals(0) ? String.format("%d", overs) : String.format("%d.%d", overs, balls);
        System.out.println(String.format("Overs: %s", overCount));
    }

    public void dismissPlayer(Player player) {
        player.dismiss();
        this.ballsCount += 1;
        this.wicketsFallen += 1;
    }

    public void incrementExtras(Integer extra) {
        this.extras += extra;
        this.totalScore += extra;
    }

    public void updateScore(Integer runs, Player player) {
        this.totalScore += runs;
        this.ballsCount += 1;
        player.updatePlayerScore(runs);
    }
}

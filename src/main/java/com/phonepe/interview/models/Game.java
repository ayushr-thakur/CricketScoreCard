package com.phonepe.interview.models;

import java.util.Scanner;

public class Game {
    private final Integer playerLimit;
    private final Integer overLimit;
    private final Team team1 = new Team("Team 1");
    private final Team team2 = new Team("Team 2");
    private Inning inning1;
    private Inning inning2;

    public Game(Integer playerLimit, Integer overLimit) {
        this.playerLimit = playerLimit;
        this.overLimit = overLimit;
    }

    private void initializePlayers(Team team) {
        for (int i = 0; i < this.playerLimit; i++) {
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            Player player = new Player(name);
            team.addPlayerInBattingOrder(player);
        }
    }

    private void initializeTeam1 () {
        System.out.println("Batting Order for Team 1:");
        initializePlayers(this.team1);
        this.inning1 = new Inning(this.team1, this.overLimit, null);
    }

    private void initializeTeam2 () {
        System.out.println("Batting Order for Team 2:");
        initializePlayers(this.team2);
        this.inning2 = new Inning(this.team2, this.overLimit, this.team1.getTotalScore()+1);
    }


    public void start() {
        this.initializeTeam1();
        this.inning1.start();
        this.initializeTeam2();
        this.inning2.start();
        this.printResult();
    }

    private void printResult() {
        String resultMessage = "Result: %s won the match by %s";
        Team winner;
        String margin;
        if (team1.getTotalScore() > team2.getTotalScore()) {
            winner = team1;
            margin = String.format("%s runs", team1.getTotalScore() - team2.getTotalScore());
            System.out.println(String.format(resultMessage, "Team 1", margin));
        } else if (team1.getTotalScore() < team2.getTotalScore()) {
            winner = team2;
            margin = String.format("%s wickets", playerLimit - 1 - team2.getWicketsFallen());
            System.out.println(String.format(resultMessage, "Team 2", margin));
        } else {
            System.out.println("Match tied!!");
        }

    }
}

package com.phonepe.interview.models;

import com.phonepe.interview.service.InputService;

public class Game {
    private final Integer playerLimit;
    private final Integer overLimit;
    private final Team team1 = new Team("Team 1");
    private final Team team2 = new Team("Team 2");
    private final InputService inputService = new InputService();
    private Inning inning1;
    private Inning inning2;

    public Game(Integer playerLimit, Integer overLimit) {
        this.playerLimit = playerLimit;
        this.overLimit = overLimit;
    }

    private void initializePlayers(Team team) {
        for (int i = 0; i < this.playerLimit; i++) {
            String name = inputService.getNextString();
            Player player = new Player(name);
            team.addPlayerInBattingOrder(player);
        }
    }

    private void startInning1() {
        System.out.println("Batting Order for Team 1:");
        initializePlayers(this.team1);
        this.inning1 = new Inning(this.team1, this.overLimit, null);
        this.inning1.start();
    }

    private void startInning2() {
        System.out.println("Batting Order for Team 2:");
        initializePlayers(this.team2);
        this.inning2 = new Inning(this.team2, this.overLimit, this.team1.getTeamScore().getRuns()+1);
        this.inning2.start();
    }

    public void start() {
        this.startInning1();
        this.startInning2();
        this.printResult();
    }

    private void printResult() {
        String resultMessage = "Result: %s won the match by %s";
        Team winner;
        String margin;
        Score team1Score = this.inning1.getInningsScore();
        Score team2Score = this.inning2.getInningsScore();
        if (team1Score.getRuns() > team2Score.getRuns()) {
            winner = team1;
            margin = String.format("%s runs", team1Score.getRuns() - team2Score.getRuns());
            System.out.printf((resultMessage) + "%n", winner.getName(), margin);
        } else if (team1Score.getRuns() < team2Score.getRuns()) {
            winner = team2;
            margin = String.format("%s wickets", playerLimit - 1 - team2Score.getWickets());
            System.out.printf((resultMessage) + "%n", winner.getName(), margin);
        } else {
            System.out.println("Match tied!!");
        }

    }
}

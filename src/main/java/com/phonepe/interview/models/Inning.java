package com.phonepe.interview.models;

import com.phonepe.interview.service.InputService;

public class Inning {
    private Player onStrike;
    private Player onNonStrike;
    private final Team battingTeam;
    private final Integer overLimit;
    private Integer ballsBowledCount = 0;
    private Integer extras = 0;
    private final Integer target;
    private final InputService inputService = new InputService();
    private final Score score = new Score(0, 0);

    public Inning(Team battingTeam, Integer overLimit, Integer target) {
        this.battingTeam = battingTeam;
        this.overLimit = overLimit;
        this.onStrike = this.battingTeam.sendNextBatsman();
        this.onNonStrike = this.battingTeam.sendNextBatsman();
        this.target = target;
    }

    private void changeStrike() {
        Player temp = onStrike;
        onStrike = onNonStrike;
        onNonStrike = temp;
    }

    private Integer makeInningsProgress(String deliveryEvent) {
        switch (deliveryEvent) {
            case "W":
                battingTeam.dismissPlayer(onStrike);
                onStrike = this.battingTeam.sendNextBatsman();
                ballsBowledCount += 1;
                return 1;
            case "Wd":
            case "N":
                extras += 1;
                return 0;
            case "1":
            case "3":
            case "5":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                this.changeStrike();
                ballsBowledCount += 1;
                return 1;
            case "0":
            case "2":
            case "4":
            case "6":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                ballsBowledCount += 1;
                return 1;
            default:
                System.out.println("Invalid delivery score");
                return 0;
        }
    }

    private void updateInningsScore() {
        this.score.setRuns(battingTeam.getTeamScore().getRuns()+extras);
        this.score.setWickets(battingTeam.getTeamScore().getWickets());
    }

    public Score getInningsScore() {
        return this.score;
    }

    public void printScoreCard() {
        System.out.printf("Scorecard for %s:%n", this.battingTeam.getName());
        System.out.printf("%s\t%s\t%s\t%s\t%s%n", "PlayerName", "Score", "4s", "6s", "Balls");
        battingTeam.printScoreCard();
        System.out.printf("Extras: %d%n", this.extras);
        this.getInningsScore().printScore();
        System.out.printf("Overs: %s%n", getOverCount());
        System.out.println();
    }

    private String getOverCount() {
        Integer overs = ballsBowledCount / 6;
        Integer balls = ballsBowledCount % 6;
        return balls.equals(0) ? String.format("%d", overs) : String.format("%d.%d", overs, balls);
    }

    private void playOneOver(Integer overNumber) {
        System.out.printf("Over %d:%n", overNumber);
        int remainingBallsInTheOver = 6;
        while (remainingBallsInTheOver > 0 && !this.isInningsOver()) {
            String deliveryEvent = inputService.getNextString();
            remainingBallsInTheOver -= this.makeInningsProgress(deliveryEvent);
            updateInningsScore();
        }
        this.printScoreCard();
        this.changeStrike();
    }

    public void start() {
        for (int overNumber = 1; overNumber <= overLimit && !isInningsOver(); overNumber++) {
            this.playOneOver(overNumber);
        }
    }

    private Boolean isInningsOver() {
        if (this.target != null && this.getInningsScore().getRuns() >= target) return true;
        else return this.onStrike == null || this.onNonStrike == null;
    }
}

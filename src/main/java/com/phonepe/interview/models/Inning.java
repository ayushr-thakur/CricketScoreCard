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

    private Integer getRunsOfExtraBall (String extraBall) {
        try {
            return Integer.parseInt(extraBall.substring(0,1));
        } catch (Exception ex) {
            return 0;
        }
    }

    private void handleExtras(Boolean updateBatsman, String deliveryEvent) {
        int extraRuns = this.getRunsOfExtraBall(deliveryEvent);
        battingTeam.updateScore(1);
        extras += 1;
        if (updateBatsman) {
            battingTeam.updateScore(extraRuns, onStrike);
        } else {
            extras += extraRuns;
            battingTeam.updateScore(extraRuns);
        }
        if (extraRuns % 2 == 1) this.changeStrike();
    }

    private Integer makeInningsProgress(String deliveryEvent) {
        try {
            int runs = Integer.parseInt(deliveryEvent);
            if (runs > 6) {
                System.out.println("Invalid delivery score");
                return 0;
            }
            battingTeam.updateScore(runs, onStrike);
            ballsBowledCount += 1;
            if (runs % 2 == 1) this.changeStrike();
            return 1;
        } catch (Exception ex) {
            if (deliveryEvent.equals("W")) {
                battingTeam.dismissPlayer(onStrike);
                onStrike = this.battingTeam.sendNextBatsman();
                ballsBowledCount += 1;
                return 1;
            } else if (deliveryEvent.contains("Wd")) {
                this.handleExtras(false, deliveryEvent);
                return 0;
            } else if (deliveryEvent.contains("N")) {
                this.handleExtras(true, deliveryEvent);
                return 0;
            } else {
                System.out.println("Invalid delivery score");
                return 0;
            }
        }
    }

    public Score getInningsScore() {
        return this.battingTeam.getTeamScore();
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

package com.phonepe.interview.models;

import com.phonepe.interview.service.InputService;

public class Inning {
    private Player onStrike;
    private Player onNonStrike;
    private final Team battingTeam;
    private final Integer overLimit;
    private final Integer target;
    private Integer ballsDelivered = 0;
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

    private Integer makeInningsProgress(String deliveryEvent) {
        switch (deliveryEvent) {
            case "W":
                battingTeam.dismissPlayer(onStrike);
                onStrike = this.battingTeam.sendNextBatsman();
                ballsDelivered += 1;
                return 1;
            case "Wd":
            case "N":
                battingTeam.incrementExtras(1);
                return 0;
            case "1":
            case "3":
            case "5":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                this.changeStrike();
                ballsDelivered += 1;
                return 1;
            case "0":
            case "2":
            case "4":
            case "6":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                ballsDelivered += 1;
                return 1;
            default:
                System.out.println("Invalid delivery score");
                return 0;
        }
    }

    private void printInningsProgress() {
        battingTeam.printScoreCard();
    }

    private void playOneOver(Integer overNumber) {
        System.out.printf("Over %d:%n", overNumber);
        int remainingBallsInTheOver = 6;
        while (remainingBallsInTheOver > 0 && !this.isInningsOver()) {
            String deliveryEvent = inputService.getNextString();
            remainingBallsInTheOver -= this.makeInningsProgress(deliveryEvent);
        }
        this.printInningsProgress();
        this.changeStrike();
    }

    public void start() {
        for (int overNumber = 1; overNumber <= overLimit && !isInningsOver(); overNumber++) {
            this.playOneOver(overNumber);
        }
    }

    private Boolean isInningsOver() {
        if (this.target != null && battingTeam.getTeamScore().getRuns() >= target) return true;
        else if (this.ballsDelivered.equals(6*this.overLimit)) return true;
        else return this.onStrike == null || this.onNonStrike == null;
    }
}

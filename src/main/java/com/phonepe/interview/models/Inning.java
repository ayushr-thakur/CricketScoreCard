package com.phonepe.interview.models;

import com.phonepe.interview.service.InputService;

public class Inning {
    private Player onStrike;
    private Player onNonStrike;
    private final Team battingTeam;
    private final Integer overLimit;
    private final Integer target;
    private Integer ballsDelivered = 0;
    private Integer overNumber = 1;
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

    private void makeInningsProgress(String deliveryEvent) {
        switch (deliveryEvent) {
            case "W":
                battingTeam.dismissPlayer(onStrike);
                onStrike = this.battingTeam.sendNextBatsman();
                ballsDelivered += 1;
                break;
            case "Wd":
            case "N":
                battingTeam.incrementExtras(1);
                break;
            case "1":
            case "3":
            case "5":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                this.changeStrike();
                ballsDelivered += 1;
                break;
            case "0":
            case "2":
            case "4":
            case "6":
                battingTeam.updateScore(Integer.valueOf(deliveryEvent), onStrike);
                ballsDelivered += 1;
                break;
        }
    }

    private void printInningsProgress() {
        battingTeam.printScoreCard();
    }

    public void start() {
        System.out.println(String.format("Over %d:", overNumber));
        while (!this.isInningsOver()) {
            String deliveryEvent = inputService.getNextString();
            this.makeInningsProgress(deliveryEvent);
            if (this.isInningsOver() || (ballsDelivered % 6 == 0 && (!deliveryEvent.equals("Wd") && !deliveryEvent.equals("N")))) {
                this.printInningsProgress();
                this.changeStrike();
                overNumber++;
                if (!this.isInningsOver()) System.out.println(String.format("Over %d:", overNumber));
            }
        }
    }

    private Boolean isInningsOver() {
        if (this.target != null && battingTeam.getTotalScore() >= target) return true;
        else if (this.ballsDelivered.equals(6*this.overLimit)) return true;
        else return this.onStrike == null || this.onNonStrike == null;
    }
}

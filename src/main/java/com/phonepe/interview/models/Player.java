package com.phonepe.interview.models;

public class Player {
    private final String name;
    private final BattingCard battingCard;
    private BattingState state;

    public Player(String name) {
        this.name = name;
        this.battingCard = new BattingCard();
        this.state = BattingState.DID_NOT_BAT;
    }

    public void updatePlayerScore(Integer runs) {
        this.battingCard.updateBattingCard(runs);
    }

    public void sendInToBat() {
        this.state = BattingState.BATTING;
    }

    public void dismiss() {
        this.updatePlayerScore(0);
        this.state = BattingState.OUT;
    }

    public void printScoreDetails() {
        String asterisk = this.state.equals(BattingState.BATTING) ? "*" : "";
        System.out.print(String.format("%s%s\t\t\t\t", this.name, asterisk));
        this.battingCard.printBattingCard();
    }
}

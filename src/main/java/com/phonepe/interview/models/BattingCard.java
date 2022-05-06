package com.phonepe.interview.models;

public class BattingCard {
    private Integer runsScored;
    private Integer ballsFaced;
    private Integer foursHit;
    private Integer sixesHit;

    public BattingCard() {
        this.runsScored = 0;
        this.ballsFaced = 0;
        this.foursHit = 0;
        this.sixesHit = 0;
    }

    public void updateBattingCard(Integer runs) {
        switch (runs) {
            case 4:
                this.foursHit += 1;
                break;
            case 6:
                this.sixesHit += 1;
                break;
        }
        this.runsScored += runs;
        this.ballsFaced += 1;
    }

    public void printBattingCard() {
        System.out.println(String.format("%d\t%d\t%d\t%d", runsScored, foursHit, sixesHit, ballsFaced));
    }
}

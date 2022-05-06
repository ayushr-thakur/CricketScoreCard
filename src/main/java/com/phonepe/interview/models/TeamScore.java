package com.phonepe.interview.models;

public class TeamScore {
    private Integer runs;
    private Integer wickets;

    public TeamScore(Integer runs, Integer wickets) {
        this.runs = runs;
        this.wickets = wickets;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public void printScore() {
        System.out.printf("Total: %d/%d%n", this.runs, this.wickets);
    }
}

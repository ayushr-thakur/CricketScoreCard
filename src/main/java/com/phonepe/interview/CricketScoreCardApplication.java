package com.phonepe.interview;

import com.phonepe.interview.models.Game;

import java.util.Scanner;

public class CricketScoreCardApplication {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("No. of players for each team: ");
		Integer playerLimit = in.nextInt();
		System.out.print("No. of overs: ");
		Integer overLimit = in.nextInt();

		Game game = new Game(playerLimit, overLimit);
		game.start();
	}
}

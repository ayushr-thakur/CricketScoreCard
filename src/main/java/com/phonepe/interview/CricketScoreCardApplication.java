package com.phonepe.interview;

import com.phonepe.interview.models.Game;
import com.phonepe.interview.service.InputService;

public class CricketScoreCardApplication {
	public static void main(String[] args) {
		final InputService inputService = new InputService();
		System.out.print("No. of players for each team: ");
		Integer playerLimit = inputService.getNextInteger();
		System.out.print("No. of overs: ");
		Integer overLimit = inputService.getNextInteger();

		Game game = new Game(playerLimit, overLimit);
		game.start();
	}
}

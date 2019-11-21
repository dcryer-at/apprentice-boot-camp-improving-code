package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();

    private LinkedList popQuestions = new LinkedList();
	private LinkedList scienceQuestions = new LinkedList();
	private LinkedList sportsQuestions = new LinkedList();
	private LinkedList rockQuestions = new LinkedList();

	private int currentPlayer = 0;
	private boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(new Player(playerName));

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	private int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (players.get(currentPlayer).isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");
                moveCurrentPlayer(roll);
            } else {
				System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

            moveCurrentPlayer(roll);
        }
		
	}

    private void moveCurrentPlayer(int roll) {
    	Player player = players.get(currentPlayer);

    	Place place = new Place(0);
    	place.setIndex(player.getPlace().getIndex() + roll);
        player.setPlace(place);

        if (place.getIndex() > 11) {
			player.setPlace(new Place(player.getPlace().getIndex() - 12));
		}

        System.out.println(players.get(currentPlayer).getName()
                + "'s new location is "
                + player.getPlace().getIndex());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		Player player = players.get(currentPlayer);
		int index = player.getPlace().getIndex();

		if (index == 0) return "Pop";
		if (index == 4) return "Pop";
		if (index == 8) return "Pop";
		if (index == 1) return "Science";
		if (index == 5) return "Science";
		if (index == 9) return "Science";
		if (index == 2) return "Sports";
		if (index == 6) return "Sports";
		if (index == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (players.get(currentPlayer).isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
                incrementCurrentPlayerCoins();

                boolean winner = didPlayerWin();
                switchToNextPlayer();

                return winner;
            } else {
				switchToNextPlayer();
				return true;
			}
			
			
			
		} else {

			System.out.println("Answer was corrent!!!!");
            incrementCurrentPlayerCoins();

            boolean winner = didPlayerWin();

            switchToNextPlayer();

            return winner;
        }
	}

    private void incrementCurrentPlayerCoins() {
		Player player = players.get(currentPlayer);
        player.setPurse(player.getPurse() + 1);
        System.out.println(players.get(currentPlayer).getName()
                + " now has "
                + player.getPurse()
                + " Gold Coins.");
    }

    private void switchToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getName()+ " was sent to the penalty box");
		players.get(currentPlayer).setInPenaltyBox(true);
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(players.get(currentPlayer).getPurse() == 6);
	}
}

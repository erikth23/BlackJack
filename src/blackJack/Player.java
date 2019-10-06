import java.util.Scanner;

/**
 * Represents a player in the game.  Keeps track of how much money they have,
 * their current bet, current hand, and whether or not they are playing.  If they
 * are not currently playing then it is considered an open seat at the table.
 */
public class Player {
	
	private Hand currentHand;
	private int money;
	private int bet;
	private boolean playing;
	private Scanner scanner;
	private String name;

    /**
     * Initializes a player and based on if they are playing or not, will
     * set it as an empty chair if not.  If they are playing, the player
     * will be initialized to not having any money.
     * @param playing
     *      Established whether or not they're playing.
     */
	public Player(boolean playing) {
		scanner = new Scanner(System.in);
		currentHand = new Hand(false);
		bet = 0;
		money = 0;
		name = "";
		this.playing = playing;
	}

    /**
     * Adds a new card to the player's hand if they are playing. Will not do
     * anything otherwise
     * @param newCard
     *      Card to be added to the hand
     */
	public void addCard(Card newCard) {
	    if(playing) {
            currentHand.add(newCard);
        }
	}

    /**
     * This will return an empty hand if the player is not playing.
     * @return Hand
     *      The current hand of the player
     */
	public Hand getHand() {
		return currentHand;
	}

    /**
     * Asks the player to make their bet and will check to make sure the bet
     * is valid(greater than 0) and that the player has enough money to make it.
     */
	void makeBet() {
		int currentMoney = this.getMoney();
		System.out.println("You have $" + currentMoney + "\nWould you like to add any money?");
		String decision = scanner.next();
		if(decision.equals("yes")) {
			this.addMoney();
		}
		System.out.println("How much would you like to bet?");
		this.bet = scanner.nextInt();
		while(this.bet > money || this.bet <= 0) {
			if(this.bet <= 0) {
				System.out.println("Please enter a bet greater than 0.");
			} else {
				System.out.println("Please either add money(add) or make a different bet(bet)");
				String add = scanner.next();
				if(add.equals("add")) {
					this.addMoney();
				}
			}
			System.out.println("How much would you like to bet?");
			this.bet = scanner.nextInt();
		}
	}

    /**
     * Removes the player from the game and turns it into an empty seat.
     */
	void removePlayer() {
		playing = false;
		money = 0;
	}

    /**
     *
     * @return boolean
     *      Returns true if the player is playing, false other wise
     */
	public boolean isPlaying() {
		return playing;
	}

    /**
     *
     * @return int
     *      How much money the user has
     */
	public int getMoney() {
		return money;
	}

    /**
     * Gets the result of the user's hand after the hand has finished.  Will
     * handle the logic for each case.
     * @param dealerTotal
     *      total of the dealer
     * @return String
     *      the result of the hand
     */
	public String getHandResult(int dealerTotal) {
		String result;
		if(currentHand.hasBlackJack()) {
			this.win();
			result = "BLACKJACK!";
		} else if(currentHand.hasBust()) {
			this.lose();
			result = "BUST!";
		} else if(currentHand.getTotal() < dealerTotal && dealerTotal <= 21) {
			this.lose();
			result = "YOU LOSE";
		} else if(currentHand.getTotal() == dealerTotal) {
			this.bet = 0;
			result = "NEITHER WIN NOR LOSE";
		} else {
			this.win();
			result = "YOU WIN";
		}
		this.currentHand.clearHand();
		return result + "\n";
	}

    /**
     *
     * @return String
     *      Name of the player
     */
	public String getName() {
		return this.name;
	}

    /**
     *
     * @return String
     *      Players name along with their hand
     */
	public String toString() {
		return this.name + ": " + currentHand;
	}

    /**
     * Adds a player to the game and asks the amount of money they would
     * like to add and how much they want to bet.
     */
    void addPlayer() {
        playing = true;
        System.out.println("Player, what is your name?");
        this.name = scanner.next();
        this.addMoney();
        this.makeBet();
    }

    /**
     * Handles the logic of the player winning.  Adds the amount bet
     * to the player's money total
     */
	private void win() {
		money += bet;
		bet = 0;
	}

    /**
     * Handles the logic of the player losing.  Subtracts the amount bet
     * from the player's money total.
     */
	private void lose() {
		money -= bet;
		bet = 0;
	}

    /**
     * Checks how much money the user would like to add and ensures it is
     * valid(greater than 0).  Then adds the respective amount.
     */
	private void addMoney() {
		System.out.println(name + ", how much money would you like to add?");
		int add = scanner.nextInt();
		while(add <= 0) {
			System.out.println("Please enter a valid amount to add(greater than 0):");
			add = scanner.nextInt();
		}
		money += add;
	}
}

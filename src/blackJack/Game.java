import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Object that represents the game(blackjack)  Allows the users to establish the table size
 * and how many players are playing each hand.  Keeps track of the deck, the players playing,
 * and the dealers hand.
 */
public class Game {

	private Deck deck;
	private Scanner scanner;
	private List<Player> players;
	private Hand dealerHand;
	private int tableSize;

	/**
	 * Initializes the game with a new deck, new dealer hand, and the table size.
	 * @param tableSize
	 * 		Number of players that can fit at the table.
	 */
	public Game(int tableSize) {
		if(tableSize > 10) {
			throw new IllegalArgumentException("Table size is too large.  Must be less than 10");
		}
		deck = new Deck();
		scanner = new Scanner(System.in);
		players = new ArrayList<Player>();
		dealerHand = new Hand(true);
		this.tableSize = tableSize;
	}

	/**
	 *
	 * @return String
	 * 		Returns the instructions of the game.
	 */
	public String getIntstructions() {
		String result = "//////////////////////////////////////////////////////////////////////////////\n" +
				";;            ;; ;;;;;;;; ;;       ;;;;;;;; ;;;;;;;;     ;;    ;;     ;;;;;;;;\n" +
				" ;;    ;;    ;;  ;;       ;;       ;;       ;;    ;;    ;;;;  ;;;;    ;;      \n" +
				"  ;;  ;;;;  ;;   ;;;;;;   ;;       ;;       ;;    ;;   ;;  ;;;;  ;;   ;;;;;;  \n" +
				"   ;;;;  ;;;;    ;;       ;;       ;;       ;;    ;;  ;;    ;;    ;;  ;;      \n" +
				"    ;;    ;;     ;;;;;;;; ;;;;;;;; ;;;;;;;; ;;;;;;;; ;;            ;; ;;;;;;;;\n" +
				"//////////////////////////////////////////////////////////////////////////////\n" +
				"Each player and the dealer will be dealt two cards from a shuffled deck. The goal\n" +
				"of the game is to get closer to 21 than the dealer without going over.  If the\n" +
				"player goes over then he loses.  At the start of each hand, each player will make\n" +
				"a bet.  For each hand The player will be asked to either hit or check.  They will\n" +
				"continue to hit until they reach 21, check, or bust.  If the player wins they will\n" +
				"gain how much they bet, but if they lose they will lose their bet.  An ace is a\n" +
				"special card that can either be a 1 or 11.  An Ace is considered soft has the value\n" +
				" of 11 prior to the player going over 21.  Once a player goes over 21 with an ace it\n" +
				" will be considered hard and will have a value of 1.  When a player joins the table\n" +
				"they will be asked their name and how much money they would like to add.  At the\n" +
				"beginning of each hand, each player will be asked if they would like to keep playing.\n" +
				"Then they will be asked if they if they want to add more money and how much they want\n" +
				"to bet.  If the table is not full, new players will have the opportunity to join before\n" +
				"each hand.  When asked a yes or no question, anything that is not a yes will be considered\n" +
				"a know.\n\nEnter any key when you're ready to play";
		return result;
	}

	/**
	 * Starts a new game and establishes the players that are playing.
	 * @param numOfPlayers
	 * 		Number of players that are playing.
	 */
	public void startNewGame(int numOfPlayers) {
		if(numOfPlayers > tableSize) {
			throw new IllegalArgumentException("The table can not exceed more than " + tableSize + " players");
		}
		while(players.size() < tableSize) {
			if(numOfPlayers > 0) {
				Player player = new Player(true);
				players.add(player);
				player.addPlayer();
				numOfPlayers--;
			} else {
				players.add(new Player(false));
			}
		}
		while(players.stream().anyMatch(Player::isPlaying)) {
			this.startNewHand();
			getDecisions();
		}
	}

	/**
	 * Starts a new hand as lined out by the instructions.  Players will be
	 * asked to make a bet and then dealt their cards.  Plays the hand in its
	 * entirety with players hitting or checking.  If the dealer has blackjack
	 * the hand will not be played and all players lose.
	 */
	private void startNewHand() {
		this.deal();
		this.displayCards();
		if(dealerHand.hasBlackJack()) {
			System.out.println("Dealer has BlackJack");
		} else {
			for(Player player : players) {
				if(player.isPlaying()) {
					this.playPlayer(player);
				}
			}
			long notBusted = players.stream().filter(p -> !p.getHand().hasBust()).count();
			if(notBusted > 0) {
				this.dealerPlay();
			}
		}
		System.out.println(this.getResults());
	}

	/**
	 * Plays an individual players hand.  Asks the player if they want to hit or check
	 * at the start and when a new card is dealt.  This will stop when the player decides
	 * to check, busts, or hits 21.
	 * @param player
	 * 		Player who's hand is being played.
	 */
	private void playPlayer(Player player) {
		System.out.println(player.getName() + " it's your turn!");
		Hand currentHand = player.getHand();
		String decision = "";
		if(currentHand.hasBlackJack()) {
			decision = "c";
			System.out.println(player.getName() + ": BLACKJACK!");
		}
		while(!decision.equals("c")) {
			System.out.println("Your Hand: " + currentHand);
			System.out.println("Would you like to hit(h) or check(c)?");
			decision = scanner.next();
			while(notValid(decision)) {
				System.out.println("Please enter a valid move: ");
				decision = scanner.next();
			}
			if(decision.equals("h")) {
				this.hit(currentHand);
			}
			if(currentHand.getTotal() == 21) {
				System.out.println(currentHand + "\n21!");
				decision = "c";
			} else if(currentHand.getTotal() > 21) {
				System.out.println(currentHand + "\nBUST");
				decision = "c";
			}
		}
	}

	/**
	 * Hits the player: adds a new card to the players hand.
	 * @param hand
	 */
	private void hit(Hand hand) {
		hand.add(deck.getTopCard());
	}

	/**
	 * Displays the hands of all the players and the dealer.
	 */
	private void displayCards() {
		for(Player player : players) {
			if(player.isPlaying()) {
				System.out.println(player);
			}
		}
		System.out.println("Dealer Hand: " + dealerHand);
		System.out.println();
	}

	/**
	 * Deals out the cards to the players and dealer to start the hand.
	 */
	private void deal() {
		for(int i = 0; i < 2; i++) {
			for(Player player : players) {
				if(player.isPlaying()){
					player.getHand().add(deck.getTopCard());
				}
			}
			dealerHand.add(deck.getTopCard());
		}
	}

	/**
	 * Plays the dealer.  Is simulated as the computer is considered to be the dealer in
	 * this game.
	 */
	private void dealerPlay() {
		System.out.println("Dealer's turn...");
		dealerHand.dealerDisplay();
		this.displayCards();
		while(dealerHand.getTotal() < 17) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.hit(dealerHand);
			this.displayCards();
		}
	}

	/**
	 * Checks if the decision of the user to hit or check is valid
	 * @param decision
	 * 		Decision to hit or check
	 * @return boolean
	 * 		returns true if the decision is not valid, false otherwise.
	 */
	private boolean notValid(String decision) {
		return !decision.equals("h") && !decision.equals("c");
	}

	/**
	 * Gets the results of the hand and whether each player won, lost, or neither.
	 * @return String
	 * 		Result of the players.
	 */
	private String getResults() {
		String result = "";
		for(Player player : players) {
			if(player.isPlaying()) {
				result += player.getName() + ": " + player.getHandResult(dealerHand.getTotal());
			}
		}
		dealerHand.clearHand();
		return result;
	}

	/**
	 * Gets the decisions of each player of whether to continue playing or not.
	 * Also checks if more players want to join the table if their is room.
	 */
	private void getDecisions() {
		for(Player player : players) {
			if(player.isPlaying()) {
				System.out.println(player.getName() + ", would you like to keep playing?");
				if(scanner.next().equals("yes")){
					player.makeBet();
				} else {
					player.removePlayer();
				}
			}
		}
		
		if(this.getNumberPlaying() < this.tableSize) {
			this.addPlayers();
		}
	}

	/**
	 * Checks if any new players have joined the table and how many.  Will add them
	 * to the game if there is new players.
	 */
	private void addPlayers() {
		System.out.println("Would you like to add any Players?");
		if(scanner.next().toLowerCase().equals("yes")) {
			System.out.println("How many players would you like to add?");
			int numAdd = scanner.nextInt();
			int playing = this.getNumberPlaying();
			while((playing + numAdd) > this.tableSize) {
				int numPossiblePlayers = tableSize - playing;
				System.out.println("You can only add up to " + numPossiblePlayers + " players");
				numAdd = scanner.nextInt();
			}
			int playerCounter = 0;
			while(numAdd > 0 && playerCounter < players.size()) {
				Player player = players.get(playerCounter);
				if(!player.isPlaying()) {
					player.addPlayer();
					numAdd--;
				}
				playerCounter++;
			}
		}
	}

	/**
	 * @return int
	 * 		Number of players playing
	 */
	private int getNumberPlaying() {
		int playing = 0;
		for(Player player : players) {
			if(player.isPlaying()) {
				playing++;
			}
		}
		return playing;
	}
}

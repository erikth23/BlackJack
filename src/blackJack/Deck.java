import java.util.Collections;
import java.util.Stack;

/**
 * Object that represents a deck of cards and provides the functionality needed
 * for blackjack specifically.
 */
public class Deck {
	
	private static final int NUMOFSHUFFLES = 1;
	//Array to represent all possible cards in their respective positions
	private static final String[] CARDNAMES = {"ACE", "TWO", "THREE", "FOUR", "FIVE",
			"SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
	
	private Stack<Card> cards;

    /**
     * Initializes the deck object to be full and shuffled
     */
	public Deck() {
		cards = new Stack<Card>();
		this.populateDeck();
		this.shuffle(1);
	}

    /**
     * Returns the next card in the deck and ensures the deck is always populated.
     * @return Card
     *      Top card on the deck
     */
	public Card getTopCard() {
		if(cards.isEmpty()) {
			this.populateDeck();
			this.shuffle(NUMOFSHUFFLES);
		}
		return cards.pop();
	}

    /**
     * Populates the deck with four of each card.
     */
	private void populateDeck() {
		for(int i = 0; i < 13; i++) {
			this.addFourCards(i);
		}
	}

    /**
     * Adds four cards.  Card is based on the position that is passed in.
     * @param position
     *      Position of the card.  Used to establish the rank and get the
     *      string value of the card.
     */
	private void addFourCards(int position) {
		String name = CARDNAMES[position];
		if(name.equals("ACE")) {
			for(int i = 0; i < 4; i++) {
				cards.add(new Ace());
			}
		} else {
            int value = position >= 10 ? 10 : position + 1;
			for(int i = 0; i < 4; i++) {
				cards.add(new Card(value, name));
			}
		}
	}

    /**
     * Shuffles the deck and allows the deck to be shuffled multiple times.
     * @param numOfShuffles
     *      Number of times the deck should be shuffled.
     */
	private void shuffle(int numOfShuffles) {
		for(int i = 0; i < numOfShuffles; i ++) {
			Collections.shuffle(cards);
		}
	} 
	
	
	
}

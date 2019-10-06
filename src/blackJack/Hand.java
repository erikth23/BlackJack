import java.util.ArrayList;
import java.util.List;

/**
 * Object that represents the hand of the player.  Contains the cards in the hand,
 * the total numerical value of the hand, and whether or not the hand belongs to the
 * dealer.
 */
public class Hand {
	
	private List<Card> cards;
	private int total;
	private boolean dealerHidden;

    /**
     * Constructor that initializes and empty hand and whether or not the
     * hand belongs to a dealer.
     * @param dealerHidden
     *      boolean to determine if the the hand belongs to a dealer
     */
	public Hand(boolean dealerHidden) {
		cards = new ArrayList<Card>();
		total = 0;
		this.dealerHidden = dealerHidden;
	}

    /**
     * Adds a card to the hand and updates the value of the hand.
     * @param newCard
     *      Card that is being added to the hand
     */
	public void add(Card newCard) {
		cards.add(newCard);
		total += newCard.getRank();
		if(this.hasBust() && this.containsSoftAce()) {
			total -= 10;
		}
	}

    /**
     *
     * @return int
     *      Total numerical value of the hand
     */
	public int getTotal() {
		return total;
	}

    /**
     * Clears the cards in the hand
     */
	public void clearHand() {
		cards.clear();
		total = 0;
	}

    /**
     * Used to display full dealer's hand.
     */
	public void dealerDisplay() {
		dealerHidden = false;
	}

    /**
     *
     * @return boolean
     *      Returns true if hand has blackjack, false otherwise
     */
	public boolean hasBlackJack() {
		return total == 21 && cards.size() == 2;
	}

    /**
     *
     * @return boolean
     *      Returns true if hand has busted, false otherwise
     */
	public boolean hasBust() {
		return total > 21;
	}

    /**
     *
     * @return boolean
     *      Returns true if hand is empty, false otherwise.
     */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

    /**
     * Only shows the second card in the dealer's hand if all players have
     * not played yet.
     *
     * @return String
     *      String representation of hand
     */
	public String toString() {
		if(dealerHidden) {
			return cards.get(1).toString();
		}
		String result = "";
		for(Card card : cards) {
			result += card + " ";
		}
		result += "Total: " + this.getTotal();
		return result;
	}

    /**
     *
     * @return boolean
     *      Returns true if the hand contains a soft Ace, false otherwise
     */
	boolean containsSoftAce() {
		for(Card card : cards) {
			if(card.toString().equals("ACE")) {
				Ace ace = (Ace)card;
				if(ace.isSoft()) {
					ace.setHard();
					return true;
				}
			}
		}
		return false;
	}

}

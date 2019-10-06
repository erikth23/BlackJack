/**
 * Object that represents a Card in the game.  It establishes the rank and name of
 * the card as well as getter methods for it.
 */
public class Card {

	/**
	 * Used to keep track of the names of the cards.  Prevents the use of an illegal
	 * card.
	 */
	enum Name
	{
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
		NINE, TEN, JACK, QUEEN, KING, ACE
	}
	
	private int rank;
	private Name name;

	/**
	 * Constructor that initiates a new card object and establishes
	 * the rank and name. Throws IllegalArgumentException if the rank
	 * passed in is greater than the established values in blackjack.
	 * @param rank
	 * 		Numerical value of the card
	 * @param name
	 * 		String value of the card
	 */
	public Card (int rank, String name) {
		if(rank > 11) {
			throw new IllegalArgumentException("An invalid value for rank was passed in");
		}
		this.name = Name.valueOf(name);
		this.rank = rank;
	}

	/**
	 *
	 * @return int
	 * 		numerical value of the card.
	 */
	public int getRank() {
		return rank;
	}

	/**
	 *
	 * @return String
	 * 		Returns the string representation of the card.
	 */
	public String toString() {
		return name.toString();
	}

}

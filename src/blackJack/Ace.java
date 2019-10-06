/**
 * Keeps track of the information for an Ace card.  Extends the card object as
 * it needs to have the same fields and values of a regular card.  Adds in
 * the hard/soft feature of blackjack that is only for the Ace card.
 */
public class Ace extends Card{

	private boolean soft;

	/**
	 * Constructor for the Ace card that establishes the initial state of the
	 * Ace card.
	 */
	public Ace() {
		super(11, "ACE");
		soft = true;
	}

	/**
	 * Establishes the Ace card as hard.  Changes the logic in blackjack so that
	 * the corresponding value is now 1.
	 */
	public void setHard() {
		soft = false;
	}

	/**
	 *
	 * @return boolean
	 * 		Returns true if Ace is soft, false otherwise.
	 */
	public boolean isSoft() {
		return soft;
	}
}

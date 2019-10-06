import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTests {
	
	@Test
	public void populateDeckTest() {
		Deck deck = new Deck();
		assertNotNull("Deck should be populated when object is contructed",
				deck.getTopCard());
	}
	
	@Test
	public void getTopCardNonEmptyTest() {
		Deck deck = new Deck();
		final int DECKSIZE = 52;
		for(int i = 0; i < DECKSIZE; i++) {
			deck.getTopCard();
		}
		Card top = deck.getTopCard();
		assertNotNull("Deck should automatically repopulate if no cards left",
				top);
	}

}

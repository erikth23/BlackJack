import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTests {
	
	@Test
	public void isPlayingTest() {
		Player player = new Player(true);
		assertTrue(player.isPlaying());
		player.removePlayer();
		assertFalse(player.isPlaying());
		assertEquals(0, player.getMoney());
	}
	
	private void getHandResultTestHelper(Player player, 
			String expected, int dealerTotal, String message) {
		String result = player.getHandResult(dealerTotal);
		assertNotNull(result);
		assertEquals(message, result, expected + "\n");
		assertTrue(player.getHand().isEmpty());
	}
	
	@Test
	public void getHandResultWinTest() {
		Player player = new Player(true);
		player.addCard(new Card(10, "KING"));
		player.addCard(new Card(8, "EIGHT"));
		this.getHandResultTestHelper(player, "YOU WIN", 
				17, "Win test failed");
	}
	
	@Test
	public void getHandResultLoseTest() {
		Player player = new Player(true);
		player.addCard(new Card(10, "KING"));
		player.addCard(new Card(8, "EIGHT"));
		this.getHandResultTestHelper(player, "YOU LOSE", 
				20, "Lose Test failed");
	}
	
	@Test
	public void getHandResultNeitherTest() {
		Player player = new Player(true);
		player.addCard(new Card(10, "KING"));
		player.addCard(new Card(8, "EIGHT"));
		this.getHandResultTestHelper(player, "NEITHER WIN NOR LOSE", 
				18, "Neither win nor lost test failed");
	}
	
	@Test
	public void getHandResultBustTest() {
		Player player = new Player(true);
		player.addCard(new Card(10, "KING"));
		player.addCard(new Card(8, "EIGHT"));
		player.addCard(new Card(5, "FIVE"));
		this.getHandResultTestHelper(player, "BUST!", 
				18, "Bust test failed");
	}
	
	public void getHandResultBlackJackTest() {
		Player player = new Player(true);
		player.addCard(new Card(1, "ACE"));
		player.addCard(new Card(10, "JACK"));
		this.getHandResultTestHelper(player, "BLACKJACK!", 
				18, "Blackjack test failed");
	}
}

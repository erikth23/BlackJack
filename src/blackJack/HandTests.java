import static org.junit.Assert.*;

import org.junit.Test;

public class HandTests {

    @Test
    public void getTotalTest() {
        Hand hand = new Hand(false);
        Card firstCard = new Card(5, "FIVE");
        Card secondCard = new Card(4, "FOUR");
        hand.add(firstCard);
        hand.add(secondCard);
        int totalExpected = firstCard.getRank() + secondCard.getRank();
        int totalActual = hand.getTotal();
        assertEquals("Get total failed", totalExpected, totalActual);
    }

    @Test
    public void dealerHiddenTest() {
        Hand hand = new Hand(true);
        Card firstCard = new Card(5, "FIVE");
        Card secondCard = new Card(4, "FOUR");
        hand.add(firstCard);
        hand.add(secondCard);
        String hiddenActual = hand.toString();
        assertEquals("Dealer hidden failed", firstCard.toString(), hiddenActual);
        hand.dealerDisplay();
        String displayExpected = "" + firstCard + " " + secondCard
                + " Total: " + hand.getTotal();
        String displayActual = hand.toString();
        assertEquals("Dealer display failed", displayExpected, displayActual);
    }

    @Test
    public void containsSoftAceTest() {
        Hand hand = new Hand(false);
        Ace ace = new Ace();
        hand.add(ace);
        hand.add(new Card(5, "FIVE"));
        assertTrue("Hand should contain a soft ace", hand.containsSoftAce());
        assertFalse("Ace should not be soft", ace.isSoft());
    }

    @Test
    public void hasBlackJackTest() {
        Hand hand = new Hand(false);
        hand.add(new Ace());
        hand.add(new Card(10, "KING"));
        assertTrue("Hand should have blackjack", hand.hasBlackJack());
    }

    @Test
    public void hasBustTest() {
        Hand hand = new Hand(false);
        hand.add(new Card(10, "QUEEN"));
        hand.add(new Card(10, "KING"));
        hand.add(new Card(2, "TWO"));
        assertTrue("Hand should be a bust", hand.hasBust());
    }

    @Test
    public void getTotalSoftAce() {
        Hand hand = new Hand(false);
        hand.add(new Ace());
        hand.add(new Card(3, "THREE"));
        assertEquals("Ace should have value of 11",
                14, hand.getTotal());
        hand.add(new Card(8, "EIGHT"));
        assertEquals("Ace should have value of 1",
                12, hand.getTotal());
    }

}

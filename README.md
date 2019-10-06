# Design Document
## Instructions
To run the game, download the code from GitHub and ensure all files are in
the same package.  Then run the Main.java file in your terminal.
This will begin the game.  To run the tests, ensure the tests 
are in the same package as the rest of the files.  Then run the
tests in your terminal.  The tests are located in the following
files: HandTests.java, DeckTests.java, PlayerTests.java.
##Design Choices
### Card and Ace
Each card was represented with a name and rank.  I choose to 
use an enum to represent the names in order to ensure the name
passed in is valid.  As well, if the project is expanded to 
create other games this will give the best base to go about 
doing that.  I choose to have a separate class for the Ace card
because of the logic with it being soft and hard.  I thought 
creating a new class would provide more readable code, rather
than say adding a boolean in the Card class that would check it.
In the constructor of the card class I ensured that the rank 
passed in is no more than 11, the maximum value a card can have.
### Deck
I choose to represent the deck as a stack because of the necessity
to take a card off the top.  For populating the deck I choose to
create a separate method to add four cards for better readability.
I represented the possible card names in an array in order to 
easily associate them with the corresponding value.  If the value
is over 10 then it will be considered as 10.  This is done so that
the for loop can iterate over all the values of the name array.
In the addFourCards method
```$xslt
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
```
I opted to have for loops inside an if-statement rather than the other
way around in order to not repeatedly check if the Card is an ace.
As well I get the name of the card prior to the for loop in order to 
not get the value from the array with each iteration of the loop.
I created the shuffle method 
```
private void shuffle(int numOfShuffles) {
  	for(int i = 0; i < numOfShuffles; i ++) {
   		Collections.shuffle(cards);
   	}
} 
```
for the future of the project so that if a high stakes game
of BlackJack were happening the deck can be more randomized. 
As well, if the project is expanded to have a game that requires
multiple shuffles then this method will allow for that.
### Hand
Unlike the Ace and Card situation, I felt the dealer aspect of a
hand could be easily represented by a boolean.  This allows me to
check in the ToString method whether or not the dealer's full hand
should be displayed or not.  
```$xslt
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
```
I choose to handle the logic of the
soft/hard ace rule within the hand class rather then within the 
card class so that I would not have to recalculate the total
value of the hand.
### Player
In order to make the game more realistic, I created a player object
that tracks the amount of money each player has and their bet.  I 
choose to handle the result logic within the hand because of how I
the win logic must be handles.  It is more readable if the all the 
money logic is handled in the player class. I decided to handle the bet
logic within the player class in order to have direct access to the
total money and current bet of the player.  I made sure to check that 
a bet made by a player is valid in both being greater than 0 and less
than the amount of money they have.  This also ensures a player can not
play without adding money.  I also validated that the money added is
valid(greater than 0).
### Game
One of the major design choices I made is populating the number of 
players in the game from the start rather than adding, removing, and
creating a new Player each time a player is added.  I made this decision
for performance reasons.  The game can be played for an infinite amount
of time and if players are coming and going with frequency the efficiency
of the program will decrease.  By just adding all the players originally
I am able to only change the values of the object.  These values would 
be set anyways if one were to add and remove players from the list.
The argument can be made that with enough players the memory of the ArrayList will be highly
inefficient, but most blackjack tables are no more than 8 people.  I also
restrict the size of the table to be no larger than 10 people.  Each
player can easily be checked to see if they're playing.  I choose
to end a player's turn if they hit 21 to be fair.  Like in real blackjack,
my game will end the hand if the dealer hits blackjack.  As well, I added
a pause in the dealer's turn when adding a new hand in order to provide
a more realistic feeling to the game.  When adding players I ensured that
the number of players could not exceed the size of the table.  I added the
ability to set the table size in order to provide more freedom to the
client of the game.  Since the dealer does not need to keep track of any
money it does not need it's own object and can thus be represented as just
a hand.
### Overall
I choose to break up the code based on each aspect of the game of blackjack.
The game is made up of multiple players who all have a hand.  Each hand is
is made up of cards which are dealt from a deck.  For every aspect in which
I iterate over a list I opted for for loops rather than a stream.  Here, I
opted for performance rather than readability.  As well, when testing the code 
it is easier to debug.  Since blackjack is best played with other players 
I opted to make it a multilayer game.
### Tests
The tests I choose to write focus on the more complex
aspects of the game.  The deck must handle the ability to get a card and ensure
that a NullPointerException is not thrown in the middle of it as that would 
decrease the user experience.  The player's money is probably the most important
because in a realistic situation people do not want to lose their money when they
are not supposed to and the other players do not want another player to gain money
when they are not supposed to.  The hand class must be tested in order to ensure
the player is not cheated out of a win or given a win when they should have
lost.
### Future
There is a lot of places where this project can improve.  The main aspect that it 
can improve upon is the user interface.  The cards and hand could be better displayed.
As well there could be better interaction with the player so that they are able to be
more aware of when it is there turn.  In terms of the game, the ability to split cards
would be a great addition.  The structure could be improved so that certain phrases
and the card names are declared in an array in a separate class that can be accessed
by the project.  As well, if I were to expand this so that more games could be played
then I would restructure the card, deck, and player classes so that they can apply
to more classes.  Another possible addition would be to setup a money transfer system
in which players are playing for real money.  This could also be set up so that an
individual could manage their own blackjack game in which they are the dealer.  This would
go along with the money transfer idea.  More test cases should also be written to 
account for all possible cases.  To go along with this, the code could be refactored
so that there is less print statements, allowing for easier to write test cases
This would work well as an MVP if we were 
trying to test if individuals would want to play BlackJack online, but there are bugs
and unaccounted for use cases that need to be solved.
## Libraries Used
1. Java 11  
I used the most updated Java library in order to have access
to the most up to date tools.
2. JUnit4  
JUnit was used to test specific classes.  I choose this library
over TestNG because of familiarity with it.



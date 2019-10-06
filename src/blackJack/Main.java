import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How large would you like your table to be?(Must not exceed 10)");
		Game game = new Game(scanner.nextInt());

		System.out.println(game.getIntstructions());
		scanner.next();
		String newGame = "yes";
		while(newGame.equals("yes")) {
			System.out.println("How many players are playing?");
			game.startNewGame(scanner.nextInt());
			System.out.println("Would like to play another game? (yes/no)");
			newGame = scanner.next();
		}
	}
}

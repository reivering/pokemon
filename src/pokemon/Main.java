package pokemon;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("Ash");
        Game game = new Game(player);
        game.startGame();
    }
}

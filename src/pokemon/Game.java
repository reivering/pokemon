package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int BATTLE_AND_CATCH = 1;
    private static final int EVENT_BATTLE = 2;
    private static final int CATCH_MODE = 3;
    private static final int EXIT = 4;

    private Scanner scanner;
    private Player player;
    private List<Pokemon> availablePokemon;

    public Game(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.availablePokemon = createPokemonList();
    }

    public void startGame() {
        System.out.println("Welcome to Pokémon Ga-Olé!");
        System.out.println("Insert 100 Yen to start.");

        while (true) {
            System.out.println("Please type '100' to insert 100 Yen:");
            String input = scanner.nextLine();
            if (input.equals("100")) break;
            else System.out.println("Invalid input. Please type '100' to insert 100 Yen.");
        }

        player.setPokemon(selectPokemon());

        while (true) {
            System.out.println("\nSelect a game mode:");
            System.out.println("1. Battle and Catch");
            System.out.println("2. Event Battle");
            System.out.println("3. Catch Mode");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case BATTLE_AND_CATCH -> battleAndCatch();
                case EVENT_BATTLE -> eventBattle();
                case CATCH_MODE -> catchMode();
                case EXIT -> {
                    System.out.println("Thank you for playing!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private List<Pokemon> createPokemonList() {
        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(new Pokemon("Pikachu", 100, 50, 30, "Electric"));
        pokemonList.add(new Pokemon("Bulbasaur", 80, 40, 25, "Grass"));
        pokemonList.add(new Pokemon("Charmander", 90, 45, 28, "Fire"));
        pokemonList.add(new Pokemon("Squirtle", 85, 42, 26, "Water"));
        return pokemonList;
    }

    private Pokemon selectPokemon() {
        System.out.println("Select your Pokémon:");
        for (int i = 0; i < availablePokemon.size(); i++) {
            Pokemon p = availablePokemon.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getType() + ")");
        }

        int choice = scanner.nextInt();
        while (choice < 1 || choice > availablePokemon.size()) {
            System.out.println("Invalid choice. Please select a valid Pokémon:");
            choice = scanner.nextInt();
        }
        return availablePokemon.get(choice - 1);
    }

    private void battleAndCatch() {
        System.out.println("Starting Battle and Catch mode...");
        Pokemon wildPokemon = new Pokemon("Bulbasaur", 80, 40, 25, "Grass");
        Battle battle = new Battle(player.getPokemon(), wildPokemon, player);
        battle.startBattle();
        if (!player.getPokemon().isDefeated() && wildPokemon.isDefeated()) {
            System.out.println("Do you want to catch " + wildPokemon.getName() + "? (yes/no)");
            scanner.nextLine(); // consume newline
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                player.setPokemon(wildPokemon);
                System.out.println("You caught " + wildPokemon.getName() + "!");
            }
        } else if (player.getPokemon().isDefeated()) {
            System.out.println("You lost the battle. Game over.");
        }
    }

    private void eventBattle() {
        System.out.println("Starting Event Battle mode...");
        Pokemon eventPokemon = new Pokemon("Eevee", 90, 50, 30, "Normal");
        Battle battle = new Battle(player.getPokemon(), eventPokemon, player);
        battle.startBattle();
        if (player.getPokemon().isDefeated()) {
            System.out.println("You lost the battle. Game over.");
        }
    }

    private void catchMode() {
        System.out.println("Starting Catch Mode...");
        Pokemon[] pokemonOptions = {
            new Pokemon("Pikachu", 100, 50, 30, "Electric"),
            new Pokemon("Bulbasaur", 80, 40, 25, "Grass"),
            new Pokemon("Charmander", 90, 45, 28, "Fire"),
            new Pokemon("Squirtle", 85, 42, 26, "Water")
        };
        for (Pokemon pokemon : pokemonOptions) {
            System.out.println("Trying to catch " + pokemon.getName() + "...");
            System.out.println("Do you want to catch " + pokemon.getName() + "? (yes/no)");
            scanner.nextLine(); // consume newline
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                player.setPokemon(pokemon);
                System.out.println("You caught " + pokemon.getName() + "!");
                return;
            }
        }
        System.out.println("No Pokémon caught.");
    }
}

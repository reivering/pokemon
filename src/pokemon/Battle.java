package pokemon;

import java.util.Random;
import java.util.Scanner;

public class Battle {
    private Pokemon playerPokemon;
    private Pokemon wildPokemon;
    private Player player;
    private Scanner scanner;
    private Random random;

    public Battle(Pokemon playerPokemon, Pokemon wildPokemon, Player player) {
        this.playerPokemon = playerPokemon;
        this.wildPokemon = wildPokemon;
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void startBattle() {
        System.out.println();
        System.out.println("Battle has started!");
        System.out.println();

        while (!playerPokemon.isDefeated() && !wildPokemon.isDefeated()) {
            playerTurn();
            if (wildPokemon.isDefeated() || playerPokemon.isDefeated()) break;
            wildTurn();
        }

        if (wildPokemon.isDefeated()) {
            System.out.println(wildPokemon.getName() + " is defeated. You won the battle!");
        } else if (playerPokemon.isDefeated()) {
            System.out.println(playerPokemon.getName() + " is defeated. You lost the battle!");
        }
    }

    private void playerTurn() {
        System.out.println("\n" + playerPokemon.getName() + "'s Turn\n");
        System.out.println("1. Attack");
        System.out.println("2. Move type");
        System.out.println("3. Double Rush");
        System.out.println("4. Rush Combo");
        System.out.println("5. Run\n");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1 -> playerAttack();
            case 2 -> playerTypeMove();
            case 3 -> playerDoubleRush();
            case 4 -> playerRushCombo();
            case 5 -> playerRun();
            default -> System.out.println("Invalid choice. Please try again.");
        }
        endTurnEffects(); // Apply end-of-turn effects
    }

    private void wildTurn() {
        System.out.println(wildPokemon.getName() + "'s Turn");
        int choice = random.nextInt(2) + 1;

        switch (choice) {
            case 1 -> wildAttack();
            case 2 -> wildTypeMove();
        }
        endTurnEffects(); // Apply end-of-turn effects
    }

    private void playerAttack() {
        int damage = calculateDamage(playerPokemon.getAttack(), wildPokemon.getDefense());
        wildPokemon.takeDamage(damage);
        playerPokemon.incrementSuccessfulAttacks(); // Track successful attacks

        System.out.println(playerPokemon.getName() + " attacked " + wildPokemon.getName() + " with " + damage + " damage.");
        displayHp(wildPokemon);
    }

    private void wildAttack() {
        int damage = calculateDamage(wildPokemon.getAttack(), playerPokemon.getDefense());
        playerPokemon.takeDamage(damage);

        System.out.println(wildPokemon.getName() + " attacked " + playerPokemon.getName() + " with " + damage + " damage.");
        displayHp(playerPokemon);
    }

    private void playerTypeMove() {
        performTypeMove(playerPokemon, wildPokemon);
    }

    private void wildTypeMove() {
        performTypeMove(wildPokemon, playerPokemon);
    }

    private void playerDoubleRush() {
        playerPokemon.useDoubleRush(wildPokemon);
    }

    private void playerRushCombo() {
        playerPokemon.activateRushCombo();
    }

    private void playerRun() {
        System.out.println(playerPokemon.getName() + " tries to run away...");
        System.out.println("But " + wildPokemon.getName() + " blocks the way!\n");
    }

    private void performTypeMove(Pokemon attacker, Pokemon defender) {
        double effectiveness = getTypeEffectiveness(attacker.getType(), defender.getType());
        int damage = calculateDamage(attacker.getAttack(), defender.getDefense(), effectiveness);

        System.out.println(attacker.getName() + " uses Type Move on " + defender.getName() + " with " + damage + " damage.");
        System.out.println(getEffectivenessMessage(effectiveness));

        defender.takeDamage(damage);
        displayHp(defender);
    }

    private int calculateDamage(int attack, int defense, double multiplier) {
        int damage = (int) ((attack * 1.5 - defense) * multiplier);
        return Math.max(damage, 0);
    }

    private int calculateDamage(int attack, int defense) {
        int damage = (int) (attack * 1.5 - defense);
        return Math.max(damage, 0);
    }

    private void displayHp(Pokemon pokemon) {
        if (pokemon.isDefeated()) {
            System.out.println(pokemon.getName() + " is defeated.");
        } else {
            System.out.println(pokemon.getName() + " has " + pokemon.getHp() + " HP left.\n");
        }
    }

    private String getEffectivenessMessage(double effectiveness) {
        if (effectiveness > 1.0) {
            return "It's super effective!";
        } else if (effectiveness < 1.0) {
            return "It's not very effective...";
        } else {
            return "";
        }
    }

    private double getTypeEffectiveness(String attackerType, String defenderType) {
        return switch (attackerType) {
            case "Electric" -> defenderType.equals("Water") ? 2.0 : defenderType.equals("Grass") ? 0.5 : 1.0;
            case "Water" -> defenderType.equals("Fire") ? 2.0 : defenderType.equals("Electric") ? 0.5 : 1.0;
            case "Fire" -> defenderType.equals("Grass") ? 2.0 : defenderType.equals("Water") ? 0.5 : 1.0;
            case "Grass" -> defenderType.equals("Water") ? 2.0 : defenderType.equals("Fire") ? 0.5 : 1.0;
            default -> 1.0;
        };
    }

    private void endTurnEffects() {
        playerPokemon.endTurnEffects();
        wildPokemon.endTurnEffects();
    }
}

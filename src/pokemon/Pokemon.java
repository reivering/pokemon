package pokemon;

import java.util.HashMap;
import java.util.Map;

public class Pokemon {
    private String name;
    private int hp;
    private int attack;
    private int defense;
    private String type;

    private boolean doubleRushAvailable;
    private boolean rushComboActivated;
    private int successfulAttacks;

    private static final Map<String, Pokemon> pokemonData = new HashMap<>();

    static {
        // Initialize Pokémon data...
    }

    public Pokemon(String name, int hp, int attack, int defense, String type) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.type = type;
        this.doubleRushAvailable = true; // By default, Double Rush is available
        this.rushComboActivated = false;
        this.successfulAttacks = 0;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getType() {
        return type;
    }

    public boolean isDefeated() {
        return hp <= 0;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0; // Ensure HP doesn't go below 0
    }

    public void useDoubleRush(Pokemon opponent) {
        if (doubleRushAvailable) {
            System.out.println(name + " uses Double Rush!");
            int damage = calculateDamage(attack, opponent.getDefense());
            opponent.takeDamage(damage);
            System.out.println(name + " attacks " + opponent.getName() + " with " + damage + " damage.");

            // Use Double Rush again
            damage = calculateDamage(attack, opponent.getDefense());
            opponent.takeDamage(damage);
            System.out.println(name + " attacks " + opponent.getName() + " again with " + damage + " damage.");
            
            doubleRushAvailable = false; // Double Rush can be used only once per battle
        } else {
            System.out.println(name + " cannot use Double Rush.");
        }
    }

    public void activateRushCombo() {
        if (rushComboActivated) {
            System.out.println(name + " uses Rush Combo!");
            // Define Rush Combo effect
            // For simplicity, let's say Rush Combo deals double damage
            rushComboActivated = false; // Rush Combo can be used only once per battle
        } else {
            System.out.println(name + " cannot use Rush Combo.");
        }
    }

    public void incrementSuccessfulAttacks() {
        successfulAttacks++;
        if (successfulAttacks >= 3) {
            rushComboActivated = true; // Activate Rush Combo after 3 successful attacks
        }
    }

    public void endTurnEffects() {
        // End of turn effects, including status effects
    }

    private int calculateDamage(int attack, int defense) {
        return (int) (attack * 1.5 - defense);
    }

    @Override
    public String toString() {
        return String.format("Pokemon{name='%s', hp=%d, attack=%d, defense=%d, type='%s'}", 
                             name, hp, attack, defense, type);
    }
}

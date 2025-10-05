package pokemon;

public class Player {
    private String name;
    private Pokemon pokemon;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}

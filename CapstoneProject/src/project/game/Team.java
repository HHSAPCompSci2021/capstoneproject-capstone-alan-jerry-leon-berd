package project.game;

/** Represents a team in the game. All enemies, bullets, and players have a team. */
public enum Team{
    none,
    player,
    enemy;

    public static Team[] all = values();

    /** Returns the id of this content. Effectively the same as ordinal(). */
    public int id(){
        return ordinal();
    }
}

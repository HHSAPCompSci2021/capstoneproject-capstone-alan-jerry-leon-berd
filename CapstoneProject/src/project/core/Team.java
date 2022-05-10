package project.core;

/** Represents a team in the game. All enemies, bullets, and players have a team. */
public enum Team{
    none,
    player,
    enemy;

    public static Team[] all = values();

    public int id(){
        return ordinal();
    }
}

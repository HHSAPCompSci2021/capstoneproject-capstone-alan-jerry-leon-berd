import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String s = "Neverend, Slowburn, Blueshift, Rubidium, Near_Miss, Collider, Superlumen, Freefall, Zeroed, Singular, Terminus, Ares, Seraphim, Parsec, Rainseed, Low_Orbit, Neverend_Waves, Slowburn_Nova, Rubidium_Core, Collider_Synced, Superlumen_Warped, Blueshift_Centered, Recursor, Nograv, Parallelism, Parallelism_Freed, Centerless, Infinitum_Genesis, Infinitum, Eclipsed";
        String[] names = s.split(", ");
        System.out.println(Arrays.toString(names));

        for (String name : names) {
            System.out.print("\"" + name + ".mp3\", ");
        }
    }
}

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String s = "shockwave, fuel_explosion, field_explosion, car_explosion, fire_explosion, laser_impact";
        String[] names = s.split(", ");
        System.out.println(Arrays.toString(names));

        for (String name : names) {
            System.out.println(name + " = new SoundEffect(\"" + name + "\");");
        }
    }
}

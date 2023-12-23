package quaternary.brokenwings.compat;

import net.minecraftforge.fml.common.Loader;
import quaternary.brokenwings.compat.bubbles.BubblesCompat;

public class BrokenWingsCompat {

    // Stub instance
    private static final BrokenWingsProxy STUB = (playerMP) -> true;

    // Compat
    public static BrokenWingsProxy BAUBLES = STUB;

    public static void init() {
        if (Loader.isModLoaded("baubles")) {
            BAUBLES = new BubblesCompat();
        }
    }
}

package quaternary.brokenwings.compat;

import net.minecraftforge.fml.common.Loader;
import quaternary.brokenwings.BrokenWings;
import quaternary.brokenwings.compat.bubbles.BubblesCompat;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class BrokenWingsCompat {

    // Stub instance
    private static final BrokenWingsProxy STUB = (playerMP) -> true;
    public static BrokenWingsProxy GAME_STAGES = STUB;

    // Compat
    public static BrokenWingsProxy BAUBLES = STUB;

    public static void init() {
        if (Loader.isModLoaded("baubles")) {
            BAUBLES = new BubblesCompat();
        }

        if (Loader.isModLoaded("gamestages")) {
            GAME_STAGES = new GameStagesCompat();
        }
    }
}

package quaternary.brokenwings.compat;

import net.minecraftforge.fml.common.Loader;
import quaternary.brokenwings.BrokenWings;
import quaternary.brokenwings.compat.bubbles.BubblesCompat;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class BrokenWingsCompat {

    // Stub instance
    private static final BrokenWingsProxy STUB = (playerMP) -> true;

    // Compat
    public static BrokenWingsProxy BAUBLES = STUB;
    public static BrokenWingsProxy GAME_STAGES = STUB;

    public static void init() {
        if (Loader.isModLoaded("baubles")) {
            BrokenWings.LOGGER.info("Baubles detected, initializing compat!");
            BAUBLES = new BubblesCompat();
        }

        if (Loader.isModLoaded("gamestages")) {
            BrokenWings.LOGGER.info("Game Stages detected, initializing compat!");
            GAME_STAGES = new GameStagesCompat();
        }
    }
}

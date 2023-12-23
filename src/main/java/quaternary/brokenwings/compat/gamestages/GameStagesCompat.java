package quaternary.brokenwings.compat.gamestages;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import quaternary.brokenwings.compat.BrokenWingsProxy;

import java.util.Map;
import java.util.Set;

/**
 * Based on <a href="https://github.com/Darkhax-Minecraft/DimensionStages/blob/1.12.2/src/main/java/net/darkhax/dimstages/DimensionStages.java">Dimension Stages</a> implementation
 */
public class GameStagesCompat implements BrokenWingsProxy {
    public static final Set<String> GLOBAL_STAGES = new ObjectArraySet<>();
    public static final Map<Integer, String> DIMENSION_MAP = new Int2ObjectOpenHashMap<>();
    public static final IntArraySet DIMENSION_ALLOW_BYPASS_KEYS = new IntArraySet();

    @Override
    public boolean isPlayerImmune(EntityPlayerMP playerMP) {
        for (String stage: GLOBAL_STAGES) {
            if (GameStageHelper.hasStage(playerMP, stage)) return true;
        }

        int dimID = playerMP.dimension;
        String requiredStage = DIMENSION_MAP.get(dimID);
        if (requiredStage == null) return true;

        return GameStageHelper.hasStage(playerMP, requiredStage) || DIMENSION_ALLOW_BYPASS_KEYS.contains(dimID);
    }
}

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
    private static final Set<String> GLOBAL_STAGES = new ObjectArraySet<>();
    private static final Map<Integer, String> DIMENSION_MAP = new Int2ObjectOpenHashMap<>();
    private static final IntArraySet DIMENSION_ALLOW_BYPASS_KEYS = new IntArraySet();

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

    /**
     * <p>Restrict dimension with stage</p>
     * <p>Added as simple GroovyScript compat</p>
     * @param stage
     * @param dimId Dimension ID
     */
    public static void addDimensionRestriction(String stage, int dimId) {
        GameStagesCompat.addDimensionRestriction(stage, dimId, false);
    }

    /**
     * <p>Restrict dimension with stage</p>
     * <p>Added as simple GroovyScript compat</p>
     * @param stage
     * @param dimId Dimension ID
     * @param allowBypassKey Allow bypass keys
     */
    public static void addDimensionRestriction(String stage, int dimId, boolean allowBypassKey) {
        GameStagesCompat.DIMENSION_MAP.put(dimId, stage);

        if (allowBypassKey) {
            GameStagesCompat.DIMENSION_ALLOW_BYPASS_KEYS.add(dimId);
        }
    }

    /**
     * <p>Add global stage to allow fly on non-blacklisted dimension</p>
     * <p>Added as simple GroovyScript compat</p>
     * @param stage
     */
    public static void addGlobalAllowStage(String stage) {
        GameStagesCompat.GLOBAL_STAGES.add(stage);
    }
}

package quaternary.brokenwings.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import quaternary.brokenwings.compat.crafttweaker.action.AddGlobalAllowStage;
import quaternary.brokenwings.compat.crafttweaker.action.AddRestrictDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.BrokenWings")
public class CraftTweakerCompat {
    @ZenMethod
    public static void addRequiredStageForDimension(String stage, int id) {
        addRequiredStageForDimension(stage, id, false);
    }

    @ZenMethod
    public static void addRequiredStageForDimension(String stage, int id, boolean allowBypassKey) {
        CraftTweakerAPI.apply(new AddRestrictDimension(stage, id, allowBypassKey));
    }

    @ZenMethod
    public static void addGlobalAllowStage(String stage) {
        CraftTweakerAPI.apply(new AddGlobalAllowStage(stage));
    }
}

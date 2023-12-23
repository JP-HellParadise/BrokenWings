package quaternary.brokenwings.compat.crafttweaker.action;

import crafttweaker.IAction;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class AddRestrictDimension implements IAction {

    private final String stage;
    private final int dimensionId;
    private final boolean allowBypassKey;

    public AddRestrictDimension(String stage, int dimensionId, boolean allowBypassKey) {

        this.stage = stage;
        this.dimensionId = dimensionId;
        this.allowBypassKey = allowBypassKey;
    }

    @Override
    public void apply() {
        GameStagesCompat.addDimensionRestriction(this.stage, this.dimensionId, this.allowBypassKey);
    }

    @Override
    public String describe() {
        return "Add required stage for dimension, with option to allow bypass keys";
    }
}

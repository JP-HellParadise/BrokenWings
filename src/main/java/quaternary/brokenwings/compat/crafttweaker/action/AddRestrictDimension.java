package quaternary.brokenwings.compat.crafttweaker.action;

import crafttweaker.IAction;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class AddRestrictDimension implements IAction {

    private final String stage;
    private final int dimensionId;

    public AddRestrictDimension(String stage, int dimensionId) {

        this.stage = stage;
        this.dimensionId = dimensionId;
    }

    @Override
    public void apply() {
        GameStagesCompat.DIMENSION_MAP.put(dimensionId, stage);
    }

    @Override
    public String describe() {
        return null;
    }
}

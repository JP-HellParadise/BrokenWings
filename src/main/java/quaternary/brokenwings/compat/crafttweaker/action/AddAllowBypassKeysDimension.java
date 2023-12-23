package quaternary.brokenwings.compat.crafttweaker.action;

import crafttweaker.IAction;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class AddAllowBypassKeysDimension implements IAction {

    private final int dimensionId;

    public AddAllowBypassKeysDimension(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    public void apply() {
        GameStagesCompat.DIMENSION_ALLOW_BYPASS_KEYS.add(this.dimensionId);
    }

    @Override
    public String describe() {
        return String.format("Dimension %d has been added to allow bypass keys", this.dimensionId);
    }
}

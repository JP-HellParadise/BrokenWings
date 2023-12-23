package quaternary.brokenwings.compat.crafttweaker.action;

import crafttweaker.IAction;
import quaternary.brokenwings.compat.gamestages.GameStagesCompat;

public class AddGlobalAllowStage implements IAction {

    private final String stage;

    public AddGlobalAllowStage(String stage) {
        this.stage = stage;
    }

    @Override
    public void apply() {
        GameStagesCompat.addGlobalAllowStage(this.stage);
    }

    @Override
    public String describe() {
        return null;
    }
}

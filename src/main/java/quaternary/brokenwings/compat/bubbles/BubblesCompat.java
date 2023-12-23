package quaternary.brokenwings.compat.bubbles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import quaternary.brokenwings.compat.BrokenWingsProxy;
import quaternary.brokenwings.config.WingConfig;

public class BubblesCompat implements BrokenWingsProxy {
	@Override
	public boolean isPlayerImmune(EntityPlayerMP playerMP) {
		IBaublesItemHandler bubbles = BaublesApi.getBaublesHandler(playerMP);
		
		for(int i = 0; i < bubbles.getSlots(); i++) {
			if(WingConfig.BUBBLE_BYPASS_KEYS.contains(bubbles.getStackInSlot(i), playerMP.dimension)) return true;
		}
		
		return false;
	}
}

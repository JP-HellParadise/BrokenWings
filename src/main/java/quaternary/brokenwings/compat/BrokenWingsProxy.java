package quaternary.brokenwings.compat;

import net.minecraft.entity.player.EntityPlayerMP;

@FunctionalInterface
public interface BrokenWingsProxy {
	/**
	 * Is this player wearing a Bauble that allows them to bypass the no-flight rule?
	 */
	boolean isPlayerImmune(EntityPlayerMP playerMP);
}

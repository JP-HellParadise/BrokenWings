package quaternary.brokenwings;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quaternary.brokenwings.brokenwings.Tags;
import quaternary.brokenwings.config.WingConfig;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class WingClientEvents {
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent e) {
		EntityPlayer player = e.getEntityPlayer();
		if(player == null || player.world == null || !WingConfig.SHOW_BYPASS_KEY_TOOLTIP) return;
		
		ItemStack i = e.getItemStack();
		boolean isArmor = WingConfig.ARMOR_BYPASS_KEYS.contains(i, player.dimension);
		boolean isInv = WingConfig.INVENTORY_BYPASS_KEYS.contains(i, player.dimension);
		boolean isBubble = WingConfig.BUBBLE_BYPASS_KEYS.contains(i, player.dimension);
		if(!isArmor && !isInv && !isBubble) return;
		
		TextFormatting color = WingConfig.MODE.isFlightInDimensionBanned(player.dimension) ? TextFormatting.GREEN : TextFormatting.RED;
		
		String lang;
		if(isArmor) {
			lang = "brokenwings.tooltip.armorBypassKey";
		} else if(isInv) {
			lang = "brokenwings.tooltip.inventoryBypassKey";
		} else { //isBubble
			lang = "brokenwings.tooltip.baubleBypassKey";
		}
		
		e.getToolTip().add(color + I18n.format(lang));
	}
}

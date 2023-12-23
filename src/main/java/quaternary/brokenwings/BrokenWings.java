package quaternary.brokenwings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.brokenwings.brokenwings.Tags;
import quaternary.brokenwings.compat.BrokenWingsCompat;
import quaternary.brokenwings.config.ListMode;
import quaternary.brokenwings.config.WingConfig;
import quaternary.brokenwings.countermeasures.Countermeasures;
import quaternary.brokenwings.countermeasures.ICountermeasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod(
	modid = Tags.MOD_ID,
	name = Tags.MOD_NAME,
	version = Tags.VERSION,
	guiFactory = "quaternary.brokenwings.config.asdf.GuiFactoryBlahblah"
)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class BrokenWings {

	public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
	
	public static final Map<String, Long> lastMessageTimes = new HashMap<>();
	public static final Random messageRandom = new Random();
	public static final int MESSAGE_COUNT = 9; //How many entries are in the lang file.
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		WingConfig.preinit(e);

		BrokenWingsCompat.init();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		Countermeasures.createAll();
		
		WingConfig.init(e);
	}
	
	//shared to prevent reallocations, i guess (it's cleared every playertick anyways)
	private static final List<ICountermeasure> usedCountermeasures = new ArrayList<>();
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void playerTick(TickEvent.PlayerTickEvent e) {
		//is the configuration set to no-op mode?
		if(WingConfig.MODE == ListMode.ALWAYS_ALLOW) return;
		
		EntityPlayer player = e.player;
		if(player.world.isRemote) return;
		
		//are they allowed to fly in this gamemode anyways?
		if(player.isCreative() || player.isSpectator()) return;
		
		//are they in a dimension that's even affected by the flight ban?
		if(!WingConfig.MODE.isFlightInDimensionBanned(player.dimension)) return;
		
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		
		//are they flying?
		usedCountermeasures.clear();
		for(ICountermeasure c : Countermeasures.ENABLED) {
			if(c.isFlying(playerMP)) {
				usedCountermeasures.add(c);
			}
		}
		if(usedCountermeasures.isEmpty()) return; //nope!
		
		//are they immune from getting their flight disabled?
		if(isPlayerImmune(playerMP)) return;
		
		//they're not immune, so stop the flight
		usedCountermeasures.forEach(c -> c.stopFlying(playerMP));
		
		//cancel their velocity
		playerMP.motionX = 0;
		playerMP.motionY -= 0.3;
		playerMP.motionZ = 0;
		playerMP./*isDirty*/isAirBorne = true; //force a velocity update
		
		//have the player accept this new velocity
		playerMP.getServerWorld().getEntityTracker().sendToTrackingAndSelf(playerMP, new SPacketEntityVelocity(playerMP));
		
		if(WingConfig.SEND_STATUS_MESSAGE || WingConfig.SHOW_PARTICLES || WingConfig.PRINT_TO_LOG) {
			long now = playerMP.getServerWorld().getTotalWorldTime();
			
			if(now - lastMessageTimes.getOrDefault(playerMP.getName(), 0L) > 20 * WingConfig.EFFECT_INTERVAL) {
				lastMessageTimes.put(playerMP.getName(), now);
				
				if(WingConfig.SEND_STATUS_MESSAGE) {
					TextComponentBase msg;
					if(WingConfig.FIXED_MESSAGE.isEmpty()) {
						msg = new TextComponentTranslation("brokenwings.dropstatus." + messageRandom.nextInt(MESSAGE_COUNT));
					} else {
						msg = new TextComponentString(WingConfig.FIXED_MESSAGE);
					}
					
					playerMP.sendStatusMessage(msg, true);
				}
				
				if(WingConfig.SHOW_PARTICLES) {
					playerMP.getServerWorld().spawnParticle(EnumParticleTypes.TOTEM, playerMP.posX, playerMP.posY, playerMP.posZ, 45, 0, 0, 0, .2);
				}
				
				if(WingConfig.PRINT_TO_LOG) {
					LOGGER.info("Dropped " + playerMP.getName() + " out of the sky.");
					LOGGER.info("Dimension: " + playerMP.dimension);
					LOGGER.info("Position: " + niceBlockPosToString(playerMP.getPosition()));
					for(ICountermeasure c : usedCountermeasures) {
						LOGGER.info("Method: " + c.getFriendlyName());
					}
				}
			}
		}
	}
	
	private static boolean isPlayerImmune(EntityPlayerMP playerMP) {
		//check for configuration
		if (WingConfig.DISABLE_BYPASS_KEYS) return false;

		//check to see if they are actually immune to the flight ban :eyes:
		//check armor
		for (ItemStack armor : playerMP.getArmorInventoryList()) {
			if (WingConfig.ARMOR_BYPASS_KEYS.contains(armor, playerMP.dimension)) return true;
		}

		//main inv + hotbar
		for	(ItemStack inv : playerMP.inventory.mainInventory) {
			if (WingConfig.INVENTORY_BYPASS_KEYS.contains(inv, playerMP.dimension)) return true;
		}

		//offhand (not included in the main inventory for reasons I guess)
		ItemStack off = playerMP.getHeldItemOffhand();
		if (WingConfig.INVENTORY_BYPASS_KEYS.contains(off, playerMP.dimension)) return true;
		
		//bubbles
		if (BrokenWingsCompat.BAUBLES.isPlayerImmune(playerMP)) return true;
		
		return false;
	}
	
	private static String niceBlockPosToString(BlockPos pos) {
		return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
	}
}

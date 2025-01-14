package quaternary.brokenwings.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.brokenwings.brokenwings.Tags;
import quaternary.brokenwings.countermeasures.Countermeasures;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class WingConfig {
	
	//General
	public static int[] DIMENSION_LIST;
	public static ListMode MODE;

	//Bypass
	public static boolean DISABLE_BYPASS_KEYS;
	public static ItemList ARMOR_BYPASS_KEYS;
	public static ItemList INVENTORY_BYPASS_KEYS;
	public static ItemList BUBBLE_BYPASS_KEYS;
	
	//Effects
	public static boolean PRINT_TO_LOG;
	public static boolean SEND_STATUS_MESSAGE;
	public static boolean SHOW_PARTICLES;
	public static int EFFECT_INTERVAL;
	public static String FIXED_MESSAGE;
	
	//Client
	public static boolean SHOW_BYPASS_KEY_TOOLTIP; 
	
	/////
	public static Configuration config;
	
	public static void preinit(FMLPreInitializationEvent e) {
		//split off, just b/c getSuggestedConfigurationFile is awesome
		//the main config is read in init, so i have access to items
		config = new Configuration(e.getSuggestedConfigurationFile(), "4");
	}
	
	public static void init(FMLInitializationEvent e) {
		config.load();
		updateConfig();
		readConfig();
	}
	
	public static void readConfig() {
		//General
		//TODO maybe ask TF for its config option.
		int[] defaultBanned = Loader.isModLoaded("twilightforest") ? new int[]{7} : new int[0];

		DIMENSION_LIST = ConfigHelpers.getIntArray(config, "dimensionIdList", "general", defaultBanned, "The list of dimension IDs, used as a allow-list or deny-list, depending on your other config settings. Internal numeric IDs, please.");

		MODE = ConfigHelpers.getEnum(config, "mode", "general", ListMode.DENY_LIST, "What mode should Broken Wings operate under?", (mode) -> {
			switch (mode) {
				case DENY_LIST: return "Flying is disabled in only the dimensions listed in \"dimensionList\".";
				case ALLOW_LIST: return "Flying is disabled in all dimensions, except the ones listed in \"dimensionList\".";
				case ALWAYS_DENY: return "Flying is always disabled, regardless of dimension ID.";
				case ALWAYS_ALLOW: return "Flying is never disabled (it's like the mod isn't even installed)";
				default: return "h";
			}
		}, ListMode.class);

		DISABLE_BYPASS_KEYS = config.getBoolean("disableBypassKeys", "bypass", false, "Disable bypass keys, force to rely on Broken Wings mode and mods compat.");

		ARMOR_BYPASS_KEYS = DISABLE_BYPASS_KEYS ?
			new ItemList() : ConfigHelpers.getItemList(config, "bypassKeyArmor", "bypass", new ItemList(), "A player wearing one of these armor pieces will be immune to the no-flight rule.");

		INVENTORY_BYPASS_KEYS = DISABLE_BYPASS_KEYS ?
			new ItemList() : ConfigHelpers.getItemList(config, "bypassKeyInventory", "bypass", new ItemList(), "A player with one of these items in their inventory will be immune to the no-flight rule.");

		BUBBLE_BYPASS_KEYS = DISABLE_BYPASS_KEYS && !Loader.isModLoaded("baubles") ?
			new ItemList() : ConfigHelpers.getItemList(config, "bypassKeyBauble", "bypass", new ItemList(), "A player wearing one of these Baubles will be immune to the no-flight rule.");
		
		//Countermeasures
		Countermeasures.readConfig(config);
		
		//Effects
		PRINT_TO_LOG = config.getBoolean("printToLog", "effects", true, "Should a message be printed to the server console when a player is dropped from the sky?");
		
		SEND_STATUS_MESSAGE = config.getBoolean("sendStatusMessage", "effects", true, "Should players receive a status message when they are dropped from the sky?");
		
		SHOW_PARTICLES = config.getBoolean("showParticles", "effects", true, "Should players create particle effects when they are dropped from the sky?");
		
		EFFECT_INTERVAL = config.getInt("effectInterval", "effects", 3, 0, Integer.MAX_VALUE, "To prevent spamming players and the server console, how many seconds will need to pass before performing another effect? (Players will still drop out of the sky if they try to fly faster than this interval.)");
		
		FIXED_MESSAGE = config.getString("fixedStatusMessage", "effects", "", "Whatever you enter here will be sent to players when they are dropped out of the sky if 'effects.sendStatusMessage' is enabled. If this is empty, I'll choose from my own internal list of (tacky) messages.").trim();
		
		//Client
		SHOW_BYPASS_KEY_TOOLTIP = DISABLE_BYPASS_KEYS
			&& config.getBoolean("showBypassKeyTooltip", "client", true, "Show a tooltip on items that are bypass-keys informing the player that they can use this item to bypass the rule.");

		if(config.hasChanged()) config.save();
	}
	
	private static void updateConfig() {
		if(config.getLoadedConfigVersion().equals("3")) {
			config.renameProperty("general", "whitelistArmor", "bypassKeyArmor");
			config.renameProperty("general", "whitelistInventory", "bypassKeyInventory");
			config.renameProperty("client", "showWhitelistTooltip", "showBypassKeyTooltip");
		}
	}
	
	static String patchEnumLol(String in) {
		switch(in) {
			case "WHITELIST": return "ALLOW_LIST";
			case "BLACKLIST": return "DENY_LIST";
			default: return in;
		}
	}
	
	@SubscribeEvent
	public static void configChange(ConfigChangedEvent e) {
		if(e.getModID().equals(Tags.MOD_ID)) {
			readConfig();
		}
	}
}

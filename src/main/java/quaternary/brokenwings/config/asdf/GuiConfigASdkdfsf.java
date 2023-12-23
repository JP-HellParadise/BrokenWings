package quaternary.brokenwings.config.asdf;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import quaternary.brokenwings.brokenwings.Tags;
import quaternary.brokenwings.config.WingConfig;

import java.util.List;
import java.util.stream.Collectors;

public class GuiConfigASdkdfsf extends GuiConfig {
	public GuiConfigASdkdfsf(GuiScreen parent) {
		super(parent, getConfigElements(), Tags.MOD_ID, false, false, Tags.MOD_NAME + " Config!");
	}
	
	//Adapted from Choonster's TestMod3. They say they adapted it from EnderIO "a while back".
	//http://www.minecraftforge.net/forum/topic/39880-110solved-make-config-options-show-up-in-gui/
	static List<IConfigElement> getConfigElements() {
		Configuration c = WingConfig.config;
		//Don't look!
		return c.getCategoryNames().stream().filter(name -> !c.getCategory(name).isChild()).map(name -> new ConfigElement(c.getCategory(name).setLanguageKey(Tags.MOD_ID + ".config." + name))).collect(Collectors.toList());
	}
}

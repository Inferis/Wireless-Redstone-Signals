package wrs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import wrs.block.WRSBlocks;
import wrs.item.WRSItemGroups;
import wrs.item.WRSItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WRS implements ModInitializer {
	public static final String MODID = "wrs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier id(String id) {
		return Identifier.of(MODID, id); 		
	}

	@Override
	public void onInitialize() {
		WRSBlocks.registerBlocks();
		WRSItems.registerItems();
		WRSItemGroups.registerItemGroups();
	}
}
package wrs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import wrs.block.WRSBlocks;
import wrs.block.entity.WRSBlockEntities;
import wrs.item.WRSItemGroups;
import wrs.item.WRSItems;
import wrs.networking.OpenConfigPanelS2CPayload;

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
		WRSBlockEntities.registerBlockEntities();
		WRSItems.registerItems();
		WRSItemGroups.registerItemGroups();

		registerNetworkPayloads();
		registerNetworkReceivers();
	}

	private void registerNetworkPayloads() {
		PayloadTypeRegistry.playS2C().register(OpenConfigPanelS2CPayload.ID, OpenConfigPanelS2CPayload.CODEC);
	}

	private void registerNetworkReceivers() {
	}
}
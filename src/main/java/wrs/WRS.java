package wrs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import wrs.block.WRSBlocks;
import wrs.block.entity.AbstractNetworkBlockEntity;
import wrs.block.entity.ReceiverBlockEntity;
import wrs.block.entity.WRSBlockEntities;
import wrs.item.WRSComponents;
import wrs.item.WRSItemGroups;
import wrs.item.WRSItems;
import wrs.networking.LinkerApplyC2SPayload;
import wrs.networking.OpenConfigPanelS2CPayload;
import wrs.networking.SaveNetworkNameC2SPayload;

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
		WRSComponents.registerComponents();
		WRSItems.registerItems();
		WRSItemGroups.registerItemGroups();
		WRSCommands.registerCommands();
		WRSSounds.registerSounds();
		
		registerNetworkPayloads();
		registerNetworkReceivers();
	}

	private void registerNetworkPayloads() {
		PayloadTypeRegistry.playS2C().register(OpenConfigPanelS2CPayload.ID, OpenConfigPanelS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SaveNetworkNameC2SPayload.ID, SaveNetworkNameC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(LinkerApplyC2SPayload.ID, LinkerApplyC2SPayload.CODEC);
	}

	private void registerNetworkReceivers() {
		ServerPlayNetworking.registerGlobalReceiver(SaveNetworkNameC2SPayload.ID, WRS::receiveSaveNetworkName);
		ServerPlayNetworking.registerGlobalReceiver(LinkerApplyC2SPayload.ID, WRS::receiveLinkerApply);
	}

	private static void receiveSaveNetworkName(SaveNetworkNameC2SPayload payload, ServerPlayNetworking.Context context) {
        if (context.player().getWorld().getBlockEntity(payload.blockPos()) instanceof AbstractNetworkBlockEntity networkBlockEntity) {
			networkBlockEntity.updateFromPayload(payload);
		}
	}

	private static void receiveLinkerApply(LinkerApplyC2SPayload payload, ServerPlayNetworking.Context context) {
		if (context.player().getWorld().getBlockEntity(payload.blockPos()) instanceof ReceiverBlockEntity receiverBlockEntity) {
			receiverBlockEntity.setNetworkName(payload.networkName());
		}
	}
}
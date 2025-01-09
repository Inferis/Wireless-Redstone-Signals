package wrs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import wrs.networking.OpenConfigPanelS2CPayload;
import wrs.screen.ConfigPanelScreen;

public class WRSClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerPayloadReceivers();
	}

	private void registerPayloadReceivers() {
		ClientPlayNetworking.registerGlobalReceiver(OpenConfigPanelS2CPayload.ID, WRSClient::receiveOpenConfigPanelPayload);
	}

	public static void receiveOpenConfigPanelPayload(OpenConfigPanelS2CPayload payload, ClientPlayNetworking.Context context) {
		context.client().setScreen(new ConfigPanelScreen(payload));
	}
}
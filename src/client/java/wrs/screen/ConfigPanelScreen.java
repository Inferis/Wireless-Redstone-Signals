package wrs.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wrs.networking.OpenConfigPanelS2CPayload;

public class ConfigPanelScreen extends Screen {
    public ConfigPanelScreen(OpenConfigPanelS2CPayload payload) {
        super(Text.translatable("screen.wrs.configpanel_screen.title"));
    }
    
    
}

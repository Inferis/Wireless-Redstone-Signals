package wrs.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import wrs.WRS;
import wrs.networking.OpenConfigPanelS2CPayload;
import wrs.networking.SaveNetworkNameC2SPayload;

public class ConfigPanelScreen extends Screen {
    public static final Identifier BACKGROUND_TEXTURE = WRS.id("textures/gui/container/config_panel.png");
    protected static final int BACKGROUND_WIDTH = 290;
    protected static final int BACKGROUND_HEIGHT = 128;

    private int originX;
    private int originY;
    private String networkName;
    private BlockPos blockPos;
    private Boolean isTransmitter;

    public TextFieldWidget networkNameTextField; 
    public ButtonWidget setButton = ButtonWidget.builder(
        Text.translatable("screen.wrs.config_panel.button.set"), button -> { setNetworkName(); })
        .dimensions(0, 0, 40, 20)
        .build();

    public ConfigPanelScreen(OpenConfigPanelS2CPayload payload) {
        super(Text.translatable(title(payload.isTransmitter())));

        networkName = payload.networkName();
        blockPos = payload.blockPos();
        isTransmitter = payload.isTransmitter();
    }

    private static String title(Boolean isTransmitter) {
        return isTransmitter ? "screen.wrs.config_panel.transmitter_title" : "screen.wrs.config_panel.receiver_title";
    }
    
    @Override
    protected void init() {
        super.init();
        
        originX = (width - BACKGROUND_WIDTH) / 2;
        originY = (height - BACKGROUND_HEIGHT) / 2;

        var rowY = originY + 60;
        
        networkNameTextField = new TextFieldWidget(textRenderer, 150, 20, Text.literal(networkName));
        networkNameTextField.setText(networkName);
        networkNameTextField.setPosition(originX + 20, rowY);
        addDrawableChild(networkNameTextField);

        setButton.setPosition(originX + 174, rowY);
        addDrawableChild(setButton);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer, 
            Text.translatable(title(isTransmitter)), 
            originX + 10, originY + 10, 0x333333, false);
        context.drawText(textRenderer, 
            Text.translatable("screen.wrs.config_panel.label.network_name"), 
            originX + 20, originY + 46, 0x333333, false);
     }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, originX, originY, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 512, 512);
    }

    private void setNetworkName() {
        String name = networkNameTextField.getText();
        if (name == null) { name = ""; }
        name = name.trim();
        if (name.length() == 0) {
            name = "Default";
        }
        networkNameTextField.setText(name);
        ClientPlayNetworking.send(new SaveNetworkNameC2SPayload(blockPos, isTransmitter, name));
    }
}

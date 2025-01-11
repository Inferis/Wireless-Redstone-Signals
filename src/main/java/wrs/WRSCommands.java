package wrs;

import java.awt.Color;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WRSCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("wrs").executes(WRSCommands::executeWRSCommand));
        });  
    }

    private static int executeWRSCommand(CommandContext<ServerCommandSource> context) {
        var result = Text.translatable("command.wrs.title").append("\n");
        for (var networkName: Networks.getNetworkNames()) {
            var transmitters = Networks.getTransmitters(networkName);
            var receivers = Networks.getReceivers(networkName);
            if (!transmitters.isEmpty() || !receivers.isEmpty()) {
                result.append(Text.literal(networkName).formatted(Formatting.AQUA)).append(":\n");
                if (!transmitters.isEmpty()) {
                    result.append("  ").append(Text.translatable("command.wrs.transmitters")).append(":\n");
                    for (var pos: transmitters) {
                        result.append("  > ").append(Text.literal(pos.getX() + ", " + pos.getY() + ", " + pos.getZ()).formatted(Formatting.YELLOW)).append("\n");
                    }
                }
                if (!receivers.isEmpty()) {
                    result.append("  ").append(Text.translatable("command.wrs.receivers")).append(":\n");
                    for (var pos: receivers) {
                        result.append("  < ").append(Text.literal(pos.getX() + ", " + pos.getY() + ", " + pos.getZ()).formatted(Formatting.LIGHT_PURPLE)).append("\n");
                    }
                }
            }
        }
        context.getSource().sendFeedback(() -> result, false);
        return 1;
    }
}

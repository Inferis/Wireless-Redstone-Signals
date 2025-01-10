package wrs.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import wrs.WRS;

public record OpenConfigPanelS2CPayload(BlockPos blockPos, Boolean isTransmitter, String networkName) implements CustomPayload {
    public static final CustomPayload.Id<OpenConfigPanelS2CPayload> ID = new CustomPayload.Id<>(WRS.id("open_config_panel_s2c_payload"));
    public static final PacketCodec<RegistryByteBuf, OpenConfigPanelS2CPayload> CODEC = PacketCodec.tuple(
        BlockPos.PACKET_CODEC, OpenConfigPanelS2CPayload::blockPos, 
        PacketCodecs.BOOLEAN, OpenConfigPanelS2CPayload::isTransmitter, 
        PacketCodecs.STRING, OpenConfigPanelS2CPayload::networkName,
        OpenConfigPanelS2CPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    
}

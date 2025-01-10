package wrs.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import wrs.WRS;

public record SaveNetworkNameC2SPayload(BlockPos blockPos, Boolean isTransmitter, String networkName) implements CustomPayload {
    public static final CustomPayload.Id<SaveNetworkNameC2SPayload> ID = new CustomPayload.Id<>(WRS.id("save_networkname_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, SaveNetworkNameC2SPayload> CODEC = PacketCodec.tuple(
        BlockPos.PACKET_CODEC, SaveNetworkNameC2SPayload::blockPos, 
        PacketCodecs.BOOLEAN, SaveNetworkNameC2SPayload::isTransmitter, 
        PacketCodecs.STRING, SaveNetworkNameC2SPayload::networkName,
        SaveNetworkNameC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

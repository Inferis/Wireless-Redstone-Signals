package wrs.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import wrs.WRS;

public record LinkerApplyC2SPayload(BlockPos blockPos, String networkName) implements CustomPayload {
    public static final CustomPayload.Id<LinkerApplyC2SPayload> ID = new CustomPayload.Id<>(WRS.id("linker_apply_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, LinkerApplyC2SPayload> CODEC = PacketCodec.tuple(
        BlockPos.PACKET_CODEC, LinkerApplyC2SPayload::blockPos,
        PacketCodecs.STRING, LinkerApplyC2SPayload::networkName,
        LinkerApplyC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    
}

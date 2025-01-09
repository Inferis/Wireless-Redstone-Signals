package wrs.block.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wrs.item.LinkerItem;
import wrs.networking.OpenConfigPanelS2CPayload;

public class ReceiverBlockEntity extends BlockEntity {
    private String networkName = "Default";

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.RECEIVER_ENTITY, pos, state);
    }

    public OpenConfigPanelS2CPayload getOpenConfigPanelPayload(BlockPos pos) {
        return new OpenConfigPanelS2CPayload(pos, networkName);
    }
}

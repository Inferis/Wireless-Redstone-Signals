package wrs.block;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import wrs.block.entity.ReceiverBlockEntity;
import wrs.item.LinkerItem;

public class ReceiverBlock extends Block {
    public ReceiverBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.POWERED);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock,
            WireOrientation wireOrientation, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);

        var power = world.getReceivedRedstonePower(pos);
        world.setBlockState(pos, state.with(Properties.POWERED, power > 0));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof ReceiverBlockEntity receiverBlockEntity) {
            if (player.getMainHandStack().getItem() instanceof LinkerItem linkerItem) {

            }
            else {
                ServerPlayNetworking.send((ServerPlayerEntity)player, receiverBlockEntity.getOpenConfigPanelPayload(pos));
            }
        }
        return ActionResult.SUCCESS;
    }
}

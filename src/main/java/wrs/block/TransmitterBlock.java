package wrs.block;

import java.util.List;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.block.WireOrientation;
import wrs.Networks;
import wrs.WRS;
import wrs.block.entity.AbstractNetworkBlockEntity;
import wrs.block.entity.ReceiverBlockEntity;
import wrs.block.entity.TransmitterBlockEntity;
import wrs.item.LinkerItem;

public class TransmitterBlock extends Block implements BlockEntityProvider {
    public TransmitterBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
            .with(Properties.POWERED, false)
            .with(Properties.POWER, 0));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TransmitterBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.POWERED);
        builder.add(Properties.POWER);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock,
            WireOrientation wireOrientation, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);

        var power = world.getReceivedRedstonePower(pos);
        world.setBlockState(pos, state
            .with(Properties.POWERED, power > 0)
            .with(Properties.POWER, power));

        updateTransmittedPower(world, pos, power);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockEntity(pos).markDirty(); // save network name

        var power = world.getReceivedRedstonePower(pos);
        updateTransmittedPower(world, pos, power);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        updateTransmittedPower(world, pos, 0);
        if (world.getBlockEntity(pos) instanceof ReceiverBlockEntity transmitterBlockEntity) {
            Networks.removeTransmitter(transmitterBlockEntity.getNetworkName(), pos);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof TransmitterBlockEntity transmitterBlockEntity) {
            if (player.getMainHandStack().getItem() instanceof LinkerItem linkerItem) {
            }
            else {
                ServerPlayNetworking.send((ServerPlayerEntity)player, transmitterBlockEntity.getOpenConfigPanelPayload(pos));
            }
            return ActionResult.SUCCESS;
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    private void updateTransmittedPower(WorldAccess world, BlockPos pos, int power) {
        if (world.getBlockEntity(pos) instanceof AbstractNetworkBlockEntity networkEntity) {
            WRS.LOGGER.info("sending power to network " + networkEntity.getNetworkName());
            for (var receiverPos: Networks.getReceivers(networkEntity.getNetworkName())) {
                if (world.getBlockEntity(receiverPos) instanceof ReceiverBlockEntity) {
                    WRS.LOGGER.info("sending power to " + receiverPos);
                    var receiverState = world.getBlockState(receiverPos);
                    receiverState = receiverState
                        .with(Properties.POWERED, power > 0)
                        .with(Properties.POWER, power);
                    world.setBlockState(receiverPos, receiverState, Block.NOTIFY_ALL);
                }
                else {
                    // doesn't exist anymore
                    Networks.removeReceiver(networkEntity.getNetworkName(), pos);
                }
            }
        }
    }
}

package wrs.block;

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
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import wrs.Networks;
import wrs.block.entity.ReceiverBlockEntity;
import wrs.item.LinkerItem;

public class ReceiverBlock extends Block implements BlockEntityProvider {
    public ReceiverBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.POWERED, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReceiverBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.POWERED);
        builder.add(Properties.POWER);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof ReceiverBlockEntity receiverBlockEntity) {
            if (player.getMainHandStack().getItem() instanceof LinkerItem linkerItem) {
            }
            else {
                ServerPlayNetworking.send((ServerPlayerEntity)player, receiverBlockEntity.getOpenConfigPanelPayload(pos));
            }
            return ActionResult.SUCCESS;
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockEntity(pos).markDirty(); // save network name
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(Properties.POWER);
    }
}

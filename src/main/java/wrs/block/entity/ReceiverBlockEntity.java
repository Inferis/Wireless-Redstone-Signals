package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;

public class ReceiverBlockEntity extends AbstractNetworkBlockEntity {
    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.RECEIVER_ENTITY, pos, state);
    }

    @Override
    protected void registerNetworkName(String networkName) {
        if (getNetworkName() != networkName) {
            Networks.removeReceiver(getNetworkName(), pos);
        }
        super.registerNetworkName(networkName);
        Networks.registerReceiver(pos, networkName);
    }

    @Override
    protected Boolean isTransmitter() {
        return false;
    }

    public void resetPower() {
        var state = world.getBlockState(pos);
        state = state.with(Properties.POWERED, false).with(Properties.POWER, 0);
        world.setBlockState(pos, state);
    }
}

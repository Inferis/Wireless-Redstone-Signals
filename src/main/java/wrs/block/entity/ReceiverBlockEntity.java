package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;
import wrs.WRS;

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
        Networks.addReceiver(pos, networkName);
    }

    @Override
    protected Boolean isTransmitter() {
        return false;
    }

    public void setNetworkName(String networkName) {
        WRS.LOGGER.info("setting network name from " + getNetworkName() + " to " + networkName);
        registerNetworkName(networkName);
        markDirty();
    }

    public void resetPower() {
        var state = world.getBlockState(pos);
        state = state.with(Properties.POWERED, false).with(Properties.POWER, 0);
        world.setBlockState(pos, state);
    }
}

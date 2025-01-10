package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;

public class ReceiverBlockEntity extends AbstractNetworkBlockEntity {
    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.RECEIVER_ENTITY, pos, state);
    }

    @Override
    protected void registerNetworkName(String networkName) {
        super.registerNetworkName(networkName);
        Networks.registerReceiver(pos, networkName);
    }
}

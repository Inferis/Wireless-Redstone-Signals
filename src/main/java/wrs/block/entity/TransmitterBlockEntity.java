package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;

public class TransmitterBlockEntity extends AbstractNetworkBlockEntity {
    public TransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.TRANSMITTER_ENTITY, pos, state);
    }    

    @Override
    protected void registerNetworkName(String networkName) {
        super.registerNetworkName(networkName);
        Networks.registerTransmitter(pos, networkName);
    }
}

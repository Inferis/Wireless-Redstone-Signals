package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;
import wrs.networking.SaveNetworkNameC2SPayload;

public class TransmitterBlockEntity extends AbstractNetworkBlockEntity {
    public TransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.TRANSMITTER_ENTITY, pos, state);
    }    

    @Override
    protected void registerNetworkName(String networkName) {
        if (!getNetworkName().equals(networkName)) {
            Networks.removeTransmitter(pos, getNetworkName());
        }
        super.registerNetworkName(networkName);
        Networks.addTransmitter(pos, networkName);
    }

    @Override
    protected Boolean isTransmitter() {
        return true;
    }
    
    @Override
    public void updateFromPayload(SaveNetworkNameC2SPayload payload) {
        for (var receiver: Networks.getReceivers(getNetworkName())) {
            if (world.getBlockEntity(receiver) instanceof ReceiverBlockEntity receiverBlockEntity) {
                receiverBlockEntity.resetPower();
            }
        }

        super.updateFromPayload(payload);
    }
}

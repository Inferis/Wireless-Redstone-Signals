package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import wrs.Networks;
import wrs.WRS;
import wrs.block.TransmitterBlock;
import wrs.networking.SaveNetworkNameC2SPayload;

public class TransmitterBlockEntity extends AbstractNetworkBlockEntity {
    public TransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.TRANSMITTER_ENTITY, pos, state);
    }    

    @Override
    protected void registerNetworkName(String networkName) {
        if (getNetworkName() != networkName) {
            Networks.removeTransmitter(getNetworkName(), pos);
        }
        super.registerNetworkName(networkName);
        Networks.registerTransmitter(pos, networkName);
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

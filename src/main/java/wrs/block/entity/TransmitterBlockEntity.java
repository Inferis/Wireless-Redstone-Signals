package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class TransmitterBlockEntity extends BlockEntity {
    public TransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(WRSBlockEntities.TRANSMITTER_ENTITY, pos, state);
    }    
}

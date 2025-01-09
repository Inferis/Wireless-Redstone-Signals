package wrs.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import wrs.WRS;
import wrs.block.WRSBlocks;

public class WRSBlockEntities {
    public static BlockEntityType<ReceiverBlockEntity> RECEIVER_ENTITY;
    public static BlockEntityType<TransmitterBlockEntity> TRANSMITTER_ENTITY;
    
    public static void registerBlockEntities() {
        RECEIVER_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            WRS.id("receiver_block_entity"),
            FabricBlockEntityTypeBuilder.create(ReceiverBlockEntity::new, WRSBlocks.RECEIVER).build()
        );    
        TRANSMITTER_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            WRS.id("transmitter_block_entity"),
            FabricBlockEntityTypeBuilder.create(TransmitterBlockEntity::new, WRSBlocks.TRANSMITTER).build()
        );    
    }

}

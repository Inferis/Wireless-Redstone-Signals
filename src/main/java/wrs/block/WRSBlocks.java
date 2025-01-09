package wrs.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import wrs.WRS;

public class WRSBlocks {
    public static ReceiverBlock RECEIVER;
    public static TransmitterBlock TRANSMITTER;

    interface BlockMaker<T extends Block> {
        T makeBlock(RegistryKey<Block> key);
    }

    private static <T extends Block> T registerBlock(String name, BlockMaker<T> blockMaker) {
        WRS.LOGGER.info("Registering block: " + WRS.MODID + ":" + name);

        var identifier = WRS.id(name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        return Registry.register(
            Registries.BLOCK,
            identifier,
            blockMaker.makeBlock(key));
    }

    public static void registerBlocks() {
        RECEIVER = registerBlock("receiver", key -> { 
            return new ReceiverBlock(AbstractBlock.Settings.copy(Blocks.REPEATER).registryKey(key)); 
        });
        TRANSMITTER = registerBlock("transmitter", key -> { 
            return new TransmitterBlock(AbstractBlock.Settings.copy(Blocks.REPEATER).registryKey(key)); 
        });
    }
}

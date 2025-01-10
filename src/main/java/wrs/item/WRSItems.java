package wrs.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import wrs.WRS;
import wrs.block.WRSBlocks;

public class WRSItems {
    public static ReceiverBlockItem RECEIVER;
    public static TransmitterBlockItem TRANSMITTER;
    public static LinkerItem LINKER;

    interface ItemMaker<T extends Item> {
        T makeItem(RegistryKey<Item> key);
    }

    private static <T extends Item> T registerItem(String name, ItemMaker<T> itemMaker) {
        WRS.LOGGER.info("Registering item: " + WRS.MODID + ":" + name);

        var identifier = WRS.id(name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, identifier);
        T item = itemMaker.makeItem(key);
        return Registry.register(Registries.ITEM, identifier, item);
    }

    public static void registerItems() {
        WRS.LOGGER.info("Registering mod items for " + WRS.MODID);

        RECEIVER = registerItem("receiver", key -> { 
            return new ReceiverBlockItem(WRSBlocks.RECEIVER, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(key)); 
        });
        TRANSMITTER = registerItem("transmitter", key -> { 
            return new TransmitterBlockItem(WRSBlocks.TRANSMITTER, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(key)); 
        });
        LINKER = registerItem("linker", key -> { 
            return new LinkerItem(new Item.Settings().useItemPrefixedTranslationKey().registryKey(key)); 
        });
    }    

}

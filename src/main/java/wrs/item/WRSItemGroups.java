package wrs.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import wrs.WRS;

public class WRSItemGroups {
    public static void registerItemGroups() {
        WRS.LOGGER.info("Registering item groups for " + WRS.MODID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(WRSItems.RECEIVER);
            content.add(WRSItems.TRANSMITTER);
        });
    }       
}

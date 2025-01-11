package wrs.item;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import wrs.WRS;

public class WRSComponents {
    public static ComponentType<String> NETWORK_NAME_COMPONENT_TYPE;

    public static void registerComponents() {
        NETWORK_NAME_COMPONENT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            WRS.id("network_name_component"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
        );    
    }
}

package wrs;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class WRSSounds {
    public static SoundEvent LINKER_PICKUP_SOUND;
    public static SoundEvent LINKER_APPLY_SOUND;
    
    public static void registerSounds() {
        LINKER_PICKUP_SOUND = Registry.register(Registries.SOUND_EVENT, WRS.id("linker_pickup"), SoundEvent.of(WRS.id("linker_pickup")));
        LINKER_APPLY_SOUND = Registry.register(Registries.SOUND_EVENT, WRS.id("linker_apply"), SoundEvent.of(WRS.id("linker_apply")));
    }
}

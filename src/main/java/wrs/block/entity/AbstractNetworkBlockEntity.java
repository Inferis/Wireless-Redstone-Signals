package wrs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;
import wrs.networking.OpenConfigPanelS2CPayload;
import wrs.networking.SaveNetworkNameC2SPayload;

public abstract class AbstractNetworkBlockEntity extends BlockEntity {
    private String networkName = "Default";

    public AbstractNetworkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public OpenConfigPanelS2CPayload getOpenConfigPanelPayload(BlockPos pos) {
        return new OpenConfigPanelS2CPayload(pos, isTransmitter(), networkName);
    }    

    abstract protected Boolean isTransmitter();
    
    @Override
    protected void readNbt(NbtCompound nbt, WrapperLookup registries) {
        super.readNbt(nbt, registries);
        registerNetworkName(nbt.getString("network_name"));
    }        

    @Override
    protected void writeNbt(NbtCompound nbt, WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putString("network_name", networkName);
        registerNetworkName(networkName);
    }

    protected void registerNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void updateFromPayload(SaveNetworkNameC2SPayload payload) {
        registerNetworkName(payload.networkName());
    }
}

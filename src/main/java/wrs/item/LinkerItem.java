package wrs.item;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import wrs.WRS;
import wrs.block.entity.ReceiverBlockEntity;
import wrs.block.entity.TransmitterBlockEntity;

public class LinkerItem extends Item {
    public LinkerItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        var name = stack.get(WRSComponents.NETWORK_NAME_COMPONENT_TYPE);
        if (name != null && name.length() > 0) {
            Text networkName = Text.literal(name).formatted(Formatting.GREEN);
            tooltip.add(Text.translatable("item.wrs.linker.tooltip.network_name").append(networkName));
        }
        else {
            tooltip.add(Text.translatable("item.wrs.linker.tooltip.not_linked"));
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var blockEntity = context.getWorld().getBlockEntity(context.getBlockPos()); 
        if (blockEntity instanceof TransmitterBlockEntity transmitterBlockEntity) {
            WRS.LOGGER.info("Setting network name on linker to " + transmitterBlockEntity.getNetworkName());
            context.getStack().set(WRSComponents.NETWORK_NAME_COMPONENT_TYPE, transmitterBlockEntity.getNetworkName());
            return ActionResult.SUCCESS;
        }
        else if (blockEntity instanceof ReceiverBlockEntity receiverBlockEntity) {
            var name = context.getStack().get(WRSComponents.NETWORK_NAME_COMPONENT_TYPE);
            if (name != null && name.length() > 0) {
                WRS.LOGGER.info("Setting network name on receiver from linker to " + name);
                receiverBlockEntity.setNetworkName(name);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}

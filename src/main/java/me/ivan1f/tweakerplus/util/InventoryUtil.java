package me.ivan1f.tweakerplus.util;

import fi.dy.masa.itemscroller.util.InventoryUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventoryUtil {
    public static final int STACK_MAX_ITEM_COUNT = 64;

    /**
     * Pick up all items from different slots. aka. double-click a slot
     *
     * @param screen the container screen
     * @param slots  slots you want to gather items from
     * @param stack  the stack you want to gather
     */
    public static void pickItemsInCursor(HandledScreen<?> screen, List<Slot> slots, ItemStack stack) {
        boolean isPickedUp = false;
        int pickedCount = 0;
        List<Slot> satisfiedSlots = slots.stream()
                .filter(slot -> InventoryUtils.areStacksEqual(slot.getStack(), stack))
                .sorted((a, b) -> b.getStack().getCount() - a.getStack().getCount())
                .collect(Collectors.toList());

        Optional<Slot> fullSlot = satisfiedSlots
                .stream()
                .filter(slot -> slot.getStack().getCount() == STACK_MAX_ITEM_COUNT)
                .findAny();
        if (fullSlot.isPresent()) {
            InventoryUtils.leftClickSlot(screen, fullSlot.get().id);
            return;
        }

        // there's no full slots

        for (Slot slot : satisfiedSlots) {
            if (pickedCount + slot.getStack().getCount() >= STACK_MAX_ITEM_COUNT) {
                // this should only be triggered after a slot has been picked up
                InventoryUtils.leftClickSlot(screen, slot.id);  // click the slot, this picks up the stack with a smaller number of items and leaves the full stack in place
                InventoryUtils.tryClearCursor(screen, MinecraftClient.getInstance());  // clear the cursor, in an empty slot
                InventoryUtils.leftClickSlot(screen, slot.id);  // pick up the full stack
                return;
            }
            InventoryUtils.leftClickSlot(screen, slot.id);
            if (isPickedUp) {
                InventoryUtils.leftClickSlot(screen, slot.id);
            } else {
                isPickedUp = true;
            }
        }
    }
}

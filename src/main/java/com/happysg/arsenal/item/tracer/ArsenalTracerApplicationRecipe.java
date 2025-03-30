package com.happysg.arsenal.item.tracer;

import com.happysg.arsenal.registry.ArsenalRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoItem;

public class ArsenalTracerApplicationRecipe extends CustomRecipe {

    public ArsenalTracerApplicationRecipe(ResourceLocation location) {
        super(location, CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack round = ItemStack.EMPTY;
        ItemStack tracer = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof AutocannonAmmoItem item) {
                if (!round.isEmpty() || item.isTracer(stack)) return false;
                round = stack;
            } else if (stack.getItem() instanceof ArsenalTracerItem tracerItem) {
                tracer = stack;
            } else {
                return false;
            }
        }

        return !round.isEmpty() && !tracer.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        ItemStack round = ItemStack.EMPTY;
        ItemStack tracer = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof AutocannonAmmoItem item) {
                if (!round.isEmpty() || item.isTracer(stack)) return ItemStack.EMPTY;
                round = stack;
            } else if (stack.getItem() instanceof ArsenalTracerItem tracerItem) {
                tracer = stack;
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (round.isEmpty() || tracer.isEmpty()) return ItemStack.EMPTY;
        ItemStack result = round.copy();
        result.setCount(1);
        if (result.getItem() instanceof AutocannonAmmoItem) {
            result.getOrCreateTag().put("arsenalTracer", tracer.save(new CompoundTag()));
        }
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ArsenalRecipes.TRACER_APPLICATION.getSerializer();
    }
}

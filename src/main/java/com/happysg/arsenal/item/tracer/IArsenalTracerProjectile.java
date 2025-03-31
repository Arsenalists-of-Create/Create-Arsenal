package com.happysg.arsenal.item.tracer;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IArsenalTracerProjectile {

    boolean isArsenalTracer();

    ItemStack getArsenalTracer();

    void setArsenalTracer(ItemStack stack);

    @Nullable
    default ArsenalTracerItem getTracerItem() {
        if (getArsenalTracer() == null) {
            return null;
        }
        if (getArsenalTracer().getItem() instanceof ArsenalTracerItem tracerItem) {
            return tracerItem;
        }
        return null;
    }
}

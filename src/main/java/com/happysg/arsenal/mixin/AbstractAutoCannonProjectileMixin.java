package com.happysg.arsenal.mixin;

import com.happysg.arsenal.item.tracer.ArsenalTracerItem;
import com.happysg.arsenal.item.tracer.IArsenalTracerProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;

@Mixin(AbstractAutocannonProjectile.class)
public abstract class AbstractAutoCannonProjectileMixin extends AbstractCannonProjectile implements IArsenalTracerProjectile {

    @Unique
    private static final EntityDataAccessor<ItemStack> ARSENAL_TRACER = SynchedEntityData.defineId(AbstractAutocannonProjectile.class, EntityDataSerializers.ITEM_STACK);

    protected AbstractAutoCannonProjectileMixin(EntityType<? extends AbstractAutocannonProjectile> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void create_Arsenal$AbstractAutoCannonProjectileMixin(EntityType type, Level level, CallbackInfo ci) {
        this.entityData.define(ARSENAL_TRACER, ItemStack.EMPTY);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void create_Arsenal$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("arsenalTracer")) {
            this.setArsenalTracer(ItemStack.of(tag.getCompound("arsenalTracer")));
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void create_Arsenal$addAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
        if (getArsenalTracer() != null && !getArsenalTracer().isEmpty()) {
            compound.put("arsenalTracer", getArsenalTracer().save(new CompoundTag()));
        }
    }

    @Override
    public boolean isArsenalTracer() {
        return getArsenalTracer() != null && !getArsenalTracer().isEmpty()
                && getArsenalTracer().getItem() instanceof ArsenalTracerItem;
    }

    @Override
    public ItemStack getArsenalTracer() {
        return this.entityData.get(ARSENAL_TRACER);
    }

    @Override
    public void setArsenalTracer(ItemStack stack) {
        this.entityData.set(ARSENAL_TRACER, stack);
    }
}

package com.happysg.arsenal.mixin;

import com.happysg.arsenal.item.tracer.IArsenalTracerProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoItem;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonCartridgeItem;

import java.util.List;

@Mixin(AutocannonCartridgeItem.class)
public abstract class AutoCannonCartridgeItemMixin extends Item implements AutocannonAmmoItem {

    public AutoCannonCartridgeItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "appendHoverText", at = @At("RETURN"))
    public void create_Arsenal$appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo ci) {
        if (stack.getOrCreateTag().contains("arsenalTracer")) {
            tooltipComponents.add(Component.translatable("tooltip.createbigcannons.tracer"));
        }
    }

    @Inject(method = "getAutocannonProjectile", at = @At("RETURN"), remap = false)
    public void create_Arsenal$getAutocannonProjectile(ItemStack stack, Level level, CallbackInfoReturnable<AbstractAutocannonProjectile> cir) {
        AbstractAutocannonProjectile projectile = cir.getReturnValue();
        if (projectile instanceof IArsenalTracerProjectile tracer && stack.getOrCreateTag().contains("arsenalTracer")) {
            ItemStack arsenalTracer = ItemStack.of(stack.getOrCreateTag().getCompound("arsenalTracer"));
            tracer.setArsenalTracer(arsenalTracer);
        }
    }

}

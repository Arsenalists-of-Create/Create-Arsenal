package com.happysg.arsenal.mixin;

import com.happysg.arsenal.item.tracer.ArsenalTracerItem;
import com.happysg.arsenal.item.tracer.IArsenalTracerProjectile;
import com.jozufozu.flywheel.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.munitions.autocannon.AbstractAutocannonProjectile;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonAmmoType;
import rbasamoyai.createbigcannons.munitions.autocannon.AutocannonProjectileRenderer;
import rbasamoyai.createbigcannons.utils.CBCUtils;

@Mixin(AutocannonProjectileRenderer.class)
public abstract class AutoCannonProjectileRendererMixin<T extends AbstractAutocannonProjectile> extends EntityRenderer<T> {

    @Unique
    private static final ResourceLocation create_Arsenal$COLOR_LOCATION = CreateBigCannons.resource("textures/entity/color.png");
    @Unique
    private static final RenderType create_Arsenal$COLOR = RenderType.entityTranslucent(create_Arsenal$COLOR_LOCATION);
    protected AutoCannonProjectileRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Shadow
    private static void renderBox(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int r, int g, int b, float length, float thickness) {
    }

    @Shadow
    private static void renderBoxInverted(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int r, int g, int b, float length, float thickness) {
    }

    @Inject(method = "render(Lrbasamoyai/createbigcannons/munitions/autocannon/AbstractAutocannonProjectile;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), remap = false, cancellable = true)
    private void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int packedLight, CallbackInfo ci) {
        if (entity instanceof IArsenalTracerProjectile tracer && tracer.isArsenalTracer()) {
            Vec3 previous = new Vec3(entity.xOld, entity.yOld, entity.zOld);
            Vec3 diff = entity.position().subtract(previous);
            double dlSqr = diff.lengthSqr();
            boolean isFastButNotTeleported = 1.0E-4 <= dlSqr && dlSqr <= entity.getDeltaMovement().lengthSqr() * 4.0;
            double diffLength = isFastButNotTeleported ? diff.length() : 0.0;
            double displacement = entity.getTotalDisplacement() - diffLength * (double) (1.0F - partialTicks);
            float length = (float) Math.min(diffLength, displacement);
            Vec3 vel = entity.getOrientation();
            if (vel.lengthSqr() < 1.0E-4) {
                vel = new Vec3(0.0, -1.0, 0.0);
            }

            poseStack.pushPose();
            if (vel.horizontalDistanceSqr() > 1.0E-4 && Math.abs(vel.y) > 0.01) {
                Vec3 horizontal = (new Vec3(vel.x, 0.0, vel.z)).normalize();
                poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(vel.normalize().reverse(), horizontal));
                poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(horizontal, new Vec3(0.0, 0.0, -1.0)));
            } else {
                poseStack.mulPoseMatrix(CBCUtils.mat4x4fFacing(vel.normalize(), new Vec3(0.0, 0.0, -1.0)));
            }

            PoseStack.Pose lastPose = poseStack.last();
            Matrix4f pose = lastPose.pose();
            Matrix3f normal = lastPose.normal();
            VertexConsumer vcons = buffers.getBuffer(create_Arsenal$COLOR);
            float thickness = entity.getAutocannonRoundType() == AutocannonAmmoType.MACHINE_GUN ? 0.03125F : 0.0625F;
            ArsenalTracerItem tracerItem = tracer.getTracerItem();
            if (tracerItem != null) {
                Color insideColor = tracerItem.colorInside;
                Color outsideColor = tracerItem.colorOutside;
                renderBox(vcons, pose, normal, insideColor.getRed(), insideColor.getGreen(), insideColor.getBlue(), length, thickness);
                renderBoxInverted(vcons, pose, normal, outsideColor.getRed(), outsideColor.getGreen(), outsideColor.getBlue(), length, thickness * 1.5F);
            }
            poseStack.popPose();
            ci.cancel();
        }
    }

    @Inject(method = "shouldRender(Lrbasamoyai/createbigcannons/munitions/autocannon/AbstractAutocannonProjectile;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At("HEAD"), cancellable = true, remap = false)
    private void shouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof IArsenalTracerProjectile tracer && tracer.isArsenalTracer()) {
            cir.setReturnValue(true);
        }
    }
}
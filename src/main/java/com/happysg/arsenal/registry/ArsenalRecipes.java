package com.happysg.arsenal.registry;

import com.google.gson.JsonObject;
import com.happysg.arsenal.CreateArsenal;
import com.happysg.arsenal.item.tracer.ArsenalTracerApplicationRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public enum ArsenalRecipes implements IRecipeTypeInfo {

    TRACER_APPLICATION(noSerializer(ArsenalTracerApplicationRecipe::new));


    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable
    private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    ArsenalRecipes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = new ResourceLocation(CreateArsenal.MODID, name);
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
    }


    public static void register(IEventBus modEventBus) {
        ShapedRecipe.setCraftingSize(9, 9);
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    private static <T extends Recipe<?>> NonNullSupplier<RecipeSerializer<?>> noSerializer(Function<ResourceLocation, T> prov) {
        return () -> new SimpleRecipeSerializer<>(prov);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    private static class SimpleRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
        private final Function<ResourceLocation, T> constructor;

        public SimpleRecipeSerializer(Function<ResourceLocation, T> constructor) {
            this.constructor = constructor;
        }

        public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            return (T) this.constructor.apply(recipeId);
        }

        public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return (T) this.constructor.apply(recipeId);
        }

        public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        }
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CreateArsenal.MODID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, CreateArsenal.MODID);
    }
}
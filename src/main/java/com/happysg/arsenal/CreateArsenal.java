package com.happysg.arsenal;

import com.happysg.arsenal.networking.ModMessages;
import com.happysg.arsenal.registry.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mod(CreateArsenal.MODID)
public class CreateArsenal {
    public static final String MODID = "create_arsenal";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateArsenal() {
        getLogger().info("Initializing Create Arsenal!");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        REGISTRATE.registerEventListeners(modEventBus);
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModProjectiles.register();
        ModCreativeTabs.register(modEventBus);
        ModLang.register();
        modEventBus.addListener(CreateArsenal::init);
        modEventBus.addListener(CreateArsenal::clientInit);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static String toHumanReadable(String key) {
        String s = key.replace("_", " ");
        s = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(s))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
        return StringUtils.normalizeSpace(s);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        ModPonderIndex.register();
        ModPonderTags.register();
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
    }
}

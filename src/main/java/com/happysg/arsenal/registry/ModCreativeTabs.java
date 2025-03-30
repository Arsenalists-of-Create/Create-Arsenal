package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import rbasamoyai.createbigcannons.ModGroup;

import java.util.function.Supplier;

import static com.happysg.arsenal.CreateArsenal.REGISTRATE;

public class ModCreativeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateArsenal.MODID);

    public static final RegistryObject<CreativeModeTab> ARSENAL_CREATIVE_TAB = addTab("arsenal", "Create: Arsenal",
            ArsenalItems.ORANGE_TRACER_TIP::asStack);


    public static RegistryObject<CreativeModeTab> addTab(String id, String name, Supplier<ItemStack> icon) {
        String itemGroupId = "itemGroup." + CreateArsenal.MODID + "." + id;
        REGISTRATE.addRawLang(itemGroupId, name);
        CreativeModeTab.Builder tabBuilder = CreativeModeTab.builder()
                .icon(icon)
                .displayItems(ModCreativeTabs::displayItems)
                .title(Components.translatable(itemGroupId))
                .withTabsBefore(ModGroup.MAIN_TAB_KEY);
        return CREATIVE_TABS.register(id, tabBuilder::build);
    }

    private static void displayItems(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output pOutput) {
        pOutput.accept(ArsenalItems.BLUE_TRACER_TIP.asStack());
        pOutput.accept(ArsenalItems.RED_TRACER_TIP.asStack());
        pOutput.accept(ArsenalItems.GREEN_TRACER_TIP.asStack());
        pOutput.accept(ArsenalItems.PINK_TRACER_TIP.asStack());
        pOutput.accept(ArsenalItems.WHITE_TRACER_TIP.asStack());
        pOutput.accept(ArsenalItems.ORANGE_TRACER_TIP.asStack());
    }


    public static void register(IEventBus eventBus) {
        CreateArsenal.getLogger().info("Registering CreativeTabs!");
        CREATIVE_TABS.register(eventBus);
    }
}

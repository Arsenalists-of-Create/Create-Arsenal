package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.happysg.arsenal.CreateArsenal.REGISTRATE;

public class ModItems {

    public static final ItemEntry<Item> BLUE_TRACER_TIP = REGISTRATE
            .item("blue_tracer_tip", Item::new)
            .register();

    public static final ItemEntry<Item> RED_TRACER_TIP = REGISTRATE
            .item("red_tracer_tip", Item::new)
            .register();

    public static final ItemEntry<Item> GREEN_TRACER_TIP = REGISTRATE
            .item("green_tracer_tip", Item::new)
            .register();

    public static final ItemEntry<Item> PINK_TRACER_TIP = REGISTRATE
            .item("pink_tracer_tip", Item::new)
            .register();

    public static final ItemEntry<Item> WHITE_TRACER_TIP = REGISTRATE
            .item("white_tracer_tip", Item::new)
            .register();

    public static final ItemEntry<Item> ORANGE_TRACER_TIP = REGISTRATE
            .item("orange_tracer_tip", Item::new)
            .register();

    public static void register() {
        CreateArsenal.getLogger().info("Registering Items!");
    }
}

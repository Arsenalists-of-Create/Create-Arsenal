package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.happysg.arsenal.item.tracer.ArsenalTracerItem;
import com.jozufozu.flywheel.util.Color;
import com.tterrag.registrate.util.entry.ItemEntry;

import static com.happysg.arsenal.CreateArsenal.REGISTRATE;

public class ArsenalItems {

    public static final ItemEntry<ArsenalTracerItem> BLUE_TRACER_TIP =
            tracerItem("blue_tracer_tip", new Color(0, 225, 255), new Color(0, 140, 255));

    public static final ItemEntry<ArsenalTracerItem> RED_TRACER_TIP =
            tracerItem("red_tracer_tip", new Color(255, 150, 150), new Color(255, 0, 16));

    public static final ItemEntry<ArsenalTracerItem> GREEN_TRACER_TIP =
            tracerItem("green_tracer_tip", new Color(182, 255, 0), new Color(42, 255, 0));

    public static final ItemEntry<ArsenalTracerItem> PINK_TRACER_TIP =
            tracerItem("pink_tracer_tip", new Color(165, 0, 255), new Color(102, 0, 255));

    public static final ItemEntry<ArsenalTracerItem> WHITE_TRACER_TIP =
            tracerItem("white_tracer_tip", new Color(255, 255, 255), new Color(255, 235, 201));

    public static final ItemEntry<ArsenalTracerItem> ORANGE_TRACER_TIP =
            tracerItem("orange_tracer_tip", new Color(255, 165, 0), new Color(255, 100, 0));

    public static void register() {
        CreateArsenal.getLogger().info("Registering Items!");
    }

    public static ItemEntry<ArsenalTracerItem> tracerItem(String name, Color colorInside, Color colorOutside) {
        return REGISTRATE
                .item(name, pBuilder -> new ArsenalTracerItem(pBuilder, colorInside, colorOutside))
                .register();
    }

}

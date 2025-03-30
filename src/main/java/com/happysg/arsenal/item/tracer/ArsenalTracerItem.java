package com.happysg.arsenal.item.tracer;

import com.jozufozu.flywheel.util.Color;
import net.minecraft.world.item.Item;

public class ArsenalTracerItem extends Item {

    public final Color colorInside;
    public final Color colorOutside;

    public ArsenalTracerItem(Properties pProperties, Color colorInside, Color colorOutside) {
        super(pProperties);
        this.colorInside = colorInside;
        this.colorOutside = colorOutside;
    }

}

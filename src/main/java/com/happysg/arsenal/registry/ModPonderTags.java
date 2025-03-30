package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.simibubi.create.foundation.ponder.PonderTag;

public class ModPonderTags {

    private static PonderTag create(String id) {
        return new PonderTag(CreateArsenal.asResource(id));
    }

    public static void register() {

    }

}

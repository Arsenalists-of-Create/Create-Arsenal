package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

public class ModPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateArsenal.MODID);

    public static void register() {
        CreateArsenal.getLogger().info("Registering Ponder!");

    }
}

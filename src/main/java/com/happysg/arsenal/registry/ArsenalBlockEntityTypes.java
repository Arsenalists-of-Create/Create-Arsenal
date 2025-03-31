package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.happysg.arsenal.block.small_gun.SmallGunBarrelBlockEntity;
import com.happysg.arsenal.block.small_gun.SmallGunBreechBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;


public class ArsenalBlockEntityTypes {

    public static final BlockEntityEntry<SmallGunBreechBlockEntity> SMALL_GUN_BREECH = CreateArsenal.REGISTRATE
            .blockEntity("small_gun_breech", SmallGunBreechBlockEntity::new)
            .validBlocks(ArsenalBlocks.SMALL_GUN_BREECH)
            .register();

    public static final BlockEntityEntry<SmallGunBarrelBlockEntity> SMALL_GUN_BARREL = CreateArsenal.REGISTRATE
            .blockEntity("small_gun_barrel", SmallGunBarrelBlockEntity::new)
            .validBlocks(ArsenalBlocks.SMALL_GUN_BARREL)
            .register();

    public static void register() {
        CreateArsenal.getLogger().info("Registering block entity types!");
    }
}

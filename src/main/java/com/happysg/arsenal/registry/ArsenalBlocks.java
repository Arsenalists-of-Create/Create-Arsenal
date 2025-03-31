package com.happysg.arsenal.registry;

import com.happysg.arsenal.CreateArsenal;
import com.happysg.arsenal.block.small_gun.SmallGunBarrelBlock;
import com.happysg.arsenal.block.small_gun.SmallGunBreechBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ArsenalBlocks {

    public static final BlockEntry<SmallGunBreechBlock> SMALL_GUN_BREECH = CreateArsenal.REGISTRATE
            .block("small_gun_breech", SmallGunBreechBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate((c, p) -> p.horizontalBlock(c.get(), blockState -> {
                String handle = blockState.getValue(SmallGunBreechBlock.HANDLES) ? "_handles" : "";
                return p.models().getExistingFile(p.modLoc("block/small_gun_breech" + handle));
            }))
            .addLayer(() -> RenderType::cutoutMipped)
            .simpleItem()
            .register();


    public static final BlockEntry<SmallGunBarrelBlock> SMALL_GUN_BARREL = CreateArsenal.REGISTRATE
            .block("small_gun_barrel", SmallGunBarrelBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate((c, p) -> p.horizontalBlock(c.get(), AssetLookup.standardModel(c, p)))
            .simpleItem()
            .register();


    public static void register() {
        CreateArsenal.getLogger().info("Registering blocks!");
    }
}

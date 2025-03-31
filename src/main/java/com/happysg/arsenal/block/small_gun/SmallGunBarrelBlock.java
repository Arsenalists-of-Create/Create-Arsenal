package com.happysg.arsenal.block.small_gun;

import com.happysg.arsenal.registry.ArsenalBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallGunBarrelBlock extends HorizontalDirectionalBlock implements IBE<SmallGunBarrelBlockEntity> {
    public static final VoxelShaper SHAPE =
            VoxelShaper.forDirectional(Block.box(7, 7, 0, 9, 9, 16), Direction.NORTH);


    public SmallGunBarrelBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE.get(pState.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection()
                        .getOpposite());
    }

    @Override
    public Class<SmallGunBarrelBlockEntity> getBlockEntityClass() {
        return SmallGunBarrelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SmallGunBarrelBlockEntity> getBlockEntityType() {
        return ArsenalBlockEntityTypes.SMALL_GUN_BARREL.get();
    }

}

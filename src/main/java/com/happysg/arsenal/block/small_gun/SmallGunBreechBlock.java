package com.happysg.arsenal.block.small_gun;

import com.happysg.arsenal.registry.ArsenalBlockEntityTypes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallGunBreechBlock extends HorizontalDirectionalBlock implements IBE<SmallGunBreechBlockEntity>, IWrenchable {
    public static final VoxelShaper SHAPE =
            VoxelShaper.forDirectional(Block.box(6, 6, 0, 10, 10, 16), Direction.NORTH);


    public static final Property<Boolean> HANDLES = BooleanProperty.create("handles");


    public SmallGunBreechBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HANDLES, false));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntityType<?> blockEntityType = getBlockEntityType();
        if (blockEntityType != null && context.getLevel().getBlockEntity(pos) instanceof SmallGunBreechBlockEntity blockEntity) {
            context.getLevel().setBlock(pos, state.setValue(HANDLES, !state.getValue(HANDLES)), 3);
            blockEntity.onWrench(!state.getValue(HANDLES));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE.get(pState.getValue(FACING));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(HANDLES);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection()
                        .getOpposite());
    }

    @Override
    public Class<SmallGunBreechBlockEntity> getBlockEntityClass() {
        return SmallGunBreechBlockEntity.class;
    }


    @Override
    public BlockEntityType<? extends SmallGunBreechBlockEntity> getBlockEntityType() {
        return ArsenalBlockEntityTypes.SMALL_GUN_BREECH.get();
    }
}

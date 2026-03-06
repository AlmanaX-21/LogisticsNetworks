package me.almana.logisticsnetworks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public class ComputerBlock extends Block {

    // Custom shape for the laptop/computer model - includes base and screen
    private static final VoxelShape BASE_SHAPE = Shapes.box(0.0, 0.0, 0.0625, 1.0, 0.0625, 1.0);
    private static final VoxelShape SCREEN_SHAPE = Shapes.box(0.0, 0.0625, 0.0, 1.0, 0.9375, 0.25);
    private static final VoxelShape SHAPE = Shapes.or(BASE_SHAPE, SCREEN_SHAPE);

    public ComputerBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.5f)
                .sound(SoundType.METAL)
                .noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

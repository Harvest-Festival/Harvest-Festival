package uk.joshiejack.harvestfestival.client.model.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.client.model.ModelCache;
import uk.joshiejack.harvestfestival.world.block.TelevisionBlock;
import uk.joshiejack.harvestfestival.world.block.entity.TelevisionBlockEntity;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.util.helper.BakedModelHelper;

import java.util.List;
import java.util.Map;


public class BakedTVModel extends BakedPenguin {


    private final Map<TVProgram, Map<Direction, List<BakedQuad>>> models = Maps.newHashMap();

    public BakedTVModel(BakedModel parent) {
        super(parent);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
        if (state != null && data.has(TelevisionBlockEntity.TV_PROGRAM)) {
            TVProgram program = data.get(TelevisionBlockEntity.TV_PROGRAM);
            if (program == null) program = TVProgram.OFF; //No Null!
            if (!models.containsKey(program)) {
                models.put(program, Maps.newHashMap());
            }

            Map<Direction, List<BakedQuad>> map = models.get(program);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(program.textureLocation());
            if (!map.containsKey(side)) {
                List<BakedQuad> quads = Lists.newArrayList();
                Direction facing = state.getValue(TelevisionBlock.FACING);
                quads.addAll(ModelCache.TELEVISION_BASE.get(state.getValue(TelevisionBlock.FACING)).getQuads(state, side, rand));
                quads.addAll(ModelCache.TELEVISION_SCREEN.get(facing)
                        .getQuads(state, side, rand)
                        .stream().map(q -> BakedModelHelper.retexture(q, sprite)).toList());
                map.put(side, ImmutableList.copyOf(quads));
            }

            //Yes my love
            return map.get(side);
        } else return super.getQuads(state, side, rand, data, renderType);
    }
}

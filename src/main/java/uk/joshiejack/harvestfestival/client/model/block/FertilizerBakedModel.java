package uk.joshiejack.harvestfestival.client.model.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.penguinlib.util.helper.BakedModelHelper;

import java.util.List;
import java.util.stream.Collectors;

public class FertilizerBakedModel extends BakedPenguin {
    private final Fertilizer fertilizer;

    public FertilizerBakedModel(BakedModel parent, Fertilizer fertilizer) {
        super(parent);
        this.fertilizer = fertilizer;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, @NotNull RandomSource pRandom) {
        TextureAtlasSprite sprite = getParticleIcon();
        return parent.getQuads(pState, pDirection, pRandom).stream().map(quad -> BakedModelHelper.retexture(quad, sprite)).collect(Collectors.toList());
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fertilizer.sprite());
    }
}

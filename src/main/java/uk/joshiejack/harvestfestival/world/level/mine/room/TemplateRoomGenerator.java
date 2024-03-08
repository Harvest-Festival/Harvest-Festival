package uk.joshiejack.harvestfestival.world.level.mine.room;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class TemplateRoomGenerator extends RoomGenerator {
    public static final Codec<TemplateRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("resource").forGetter(generator -> generator.id)
    ).apply(instance, TemplateRoomGenerator::new));

    private static final boolean ON_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
    private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
    private final List<Modified> extra = Lists.newArrayList();
    protected int[][] data;
    private final int width;
    private final int height;
    private final ResourceLocation id;

    public TemplateRoomGenerator(ResourceLocation id) {
        this.id = id;
        ResourceLocation resource = new ResourceLocation(id.getNamespace(), "mine_rooms/" + id.getPath() + ".png");
        //BufferedImage image = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream());
        InputStream stream = null;
        String s = "/data/" + resource.getNamespace() + "/" + resource.getPath();
        try {
            URL url = TemplateRoomGenerator.class.getResource(s);
            if (url != null && validatePath(new File(url.getFile()), s))
                stream = TemplateRoomGenerator.class.getResourceAsStream(s);
        } catch (IOException var4) {
            stream = TemplateRoomGenerator.class.getResourceAsStream(s);
        }

        try {
            assert stream != null;
            BufferedImage image = ImageIO.read(stream);
            width = image.getWidth();
            height = image.getHeight();
            data = new int[height][width];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    data[row][col] = image.getRGB(col, row);
                }
            }

            init(resource);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template: " + resource, e);
        }
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    private boolean validatePath(File p_191384_0_, String p_191384_1_) throws IOException {
        String s = p_191384_0_.getCanonicalPath();

        if (ON_WINDOWS) {
            s = BACKSLASH_MATCHER.replaceFrom(s, '/');
        }

        return s.endsWith(p_191384_1_);
    }

    public void init(ResourceLocation resource) throws IOException {
        //Add the rotations
        if (width == height) {
            extra.add(new Modified(id, 0, true));
            for (int i = 1; i <= 3; i++) {
                extra.add(new Modified(id, i, true));
                extra.add(new Modified(id, i, false));
            }
        }
    }

    @Override
    public boolean canGenerate(MineSettings settings, BlockPos pos) {
        int buffer = (Math.max(width, height) + 1);
        int arrayMax = (settings.chunksPerMine() * 16) - buffer;
        return pos.getX() > buffer && pos.getZ() > buffer && pos.getX() < arrayMax && pos.getZ() < arrayMax;
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladder) {
        RandomSource rand = world.rand;
        List<BlockPos> offsets = Lists.newArrayList();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == -1) {
                    offsets.add(new BlockPos(i, 0, j));
                }
            }
        }

        BlockPos offset = offsets.get(rand.nextInt(offsets.size()));
        List<BlockPos> positions = Lists.newArrayList();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == -1) {
                    BlockPos target = ladder.offset(-offset.getX() + i, 0, -offset.getZ() + j); //new BlockPos(x, 0, z);
                    world.setBlockState(target, world.tier.getFloor(world.floor));
                    int yNum = world.settings.floorHeight() / 2;
                    for (int y = 1; y < yNum + rand.nextInt(yNum); y++) {
                        world.setBlockState(target.above(y), Blocks.AIR.defaultBlockState());
                    }

                    positions.add(target);
                }
            }
        }

        return getLadderPosition(world, positions, 10, ladder);
    }

    public List<Modified> extra() {
        return extra;
    }

    public static class Modified extends TemplateRoomGenerator {
        public Modified(ResourceLocation id, int amount, boolean mirrored) {
            super(id);
            this.data = mirrored ? mirror(data) : data;
            for (int i = 0; i < amount; i++) {
                this.data = rotate(data);
            }
        }

        @Override
        public void init(ResourceLocation resource) {
        }

        private int[][] mirror(int[][] array) {
            int size = array.length;
            int ret[][] = new int[size][size];
            for (int i = size - 1; i >= 0; i--) {
                for (int j = size - 1; j >= 0; j--) {
                    ret[size - 1 - i][size - 1 - j] = array[i][j];
                }
            }

            return ret;
        }

        private int[][] rotate(int[][] array) {
            int size = array.length;
            int[][] ret = new int[size][size];
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    ret[i][j] = array[size - j - 1][i];

            return ret;
        }
    }
}

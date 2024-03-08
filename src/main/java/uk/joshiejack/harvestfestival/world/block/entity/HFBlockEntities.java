package uk.joshiejack.harvestfestival.world.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;

@SuppressWarnings("ConstantConditions")
public class HFBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HarvestFestival.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MailboxBlockEntity>> MAILBOX = BLOCK_ENTITY_TYPES.register("mailbox", () ->
            BlockEntityType.Builder.of(MailboxBlockEntity::new, HFBlocks.MAILBOX.get(), HFBlocks.OAK_MAILBOX.get(),
                    HFBlocks.SPRUCE_MAILBOX.get(), HFBlocks.BIRCH_MAILBOX.get(), HFBlocks.JUNGLE_MAILBOX.get(),
                    HFBlocks.ACACIA_MAILBOX.get(), HFBlocks.DARK_OAK_MAILBOX.get(), HFBlocks.NETHER_BRICK_MAILBOX.get(),
                    HFBlocks.CHERRY_MAILBOX.get(), HFBlocks.MANGROVE_MAILBOX.get(), HFBlocks.CRIMSON_MAILBOX.get(),
                    HFBlocks.WARPED_MAILBOX.get(), HFBlocks.BAMBOO_MAILBOX.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TelevisionBlockEntity>> TELEVISION = BLOCK_ENTITY_TYPES.register("television", () ->
            BlockEntityType.Builder.of(TelevisionBlockEntity::new, HFBlocks.TELEVISION.get()).build(null));
}
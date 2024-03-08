package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.settlements.api.SettlementsAPI;
import uk.joshiejack.settlements.api.npc.NPC;
import uk.joshiejack.settlements.api.town.Town;

import java.util.Optional;
import java.util.function.Consumer;

public class HFFluids {
    public static final ResourceLocation HARVEST_GODDESS = new ResourceLocation(HarvestFestival.MODID, "harvest_goddess");
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, HarvestFestival.MODID);
    public static final DeferredHolder<FluidType, FluidType> GODDESS_WATER_TYPE = FLUID_TYPES.register("goddess_water", () -> new FluidType(FluidType.Properties.create()
            .descriptionId("block." + HarvestFestival.MODID + ".goddess_water")
            .fallDistanceModifier(0F)
            .canExtinguish(true)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canHydrate(true)) {

        @Override
        public void setItemMovement(ItemEntity entity) {
            if (!entity.level().isClientSide && !entity.getItem().is(HFTags.Items.UNGIFTABLE)) {
                //TODO: Using Settlements API. get the harvest_goddess
                Optional<Town> town = SettlementsAPI.instance.towns().getTown(entity.level(), entity.blockPosition());
                Optional<NPC> goddess = town.flatMap(t -> t.getOrCreateNPC(HARVEST_GODDESS));
                goddess.ifPresent(npc -> {
                    //TODO: Set goddess to only drop flower when she is spawned in using the flower method
                    //TODO: Gifting Mechanics called on goddess when item dropped in pond
                });

                //If the item isn't ungiftable then apply the gifting mechanic to it
            } else super.setItemMovement(entity);
        }

        @Override
        public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
            if (reader instanceof Level level) {
                return level.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
            }
            //Best guess fallback to default (true)
            return super.canConvertToSource(state, reader, pos);
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                private static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png"),
                        WATER_STILL = new ResourceLocation(HarvestFestival.MODID, "block/goddess_water_still"),
                        WATER_FLOW = new ResourceLocation(HarvestFestival.MODID, "block/goddess_water_flow"),
                        WATER_OVERLAY = new ResourceLocation(HarvestFestival.MODID, "block/water_overlay");

                @Override
                public ResourceLocation getStillTexture() {
                    return WATER_STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return WATER_FLOW;
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return WATER_OVERLAY;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return UNDERWATER_LOCATION;
                }
            });
        }
    });

    //Icy Water
    public static final DeferredHolder<FluidType, FluidType> ICY_WATER_TYPE = FLUID_TYPES.register("icy_water", () -> new FluidType(FluidType.Properties.create()
            .descriptionId("block." + HarvestFestival.MODID + ".icy_water")
            .fallDistanceModifier(0F)
            .canExtinguish(true)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .temperature(-40)
            .canHydrate(true)) {

        @Override
        public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
            if (reader instanceof Level level) {
                return level.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
            }
            //Best guess fallback to default (true)
            return super.canConvertToSource(state, reader, pos);
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                private static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png"),
                        WATER_STILL = new ResourceLocation(HarvestFestival.MODID, "block/icy_water_still"),
                        WATER_FLOW = new ResourceLocation(HarvestFestival.MODID, "block/icy_water_flow"),
                        WATER_OVERLAY = new ResourceLocation(HarvestFestival.MODID, "block/water_overlay");

                @Override
                public ResourceLocation getStillTexture() {
                    return WATER_STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return WATER_FLOW;
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return WATER_OVERLAY;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return UNDERWATER_LOCATION;
                }
            });
        }
    });


    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, HarvestFestival.MODID);
    public static final DeferredHolder<Fluid, Fluid> GODDESS_WATER_STILL = FLUIDS.register("goddess_water_still", () -> new BaseFlowingFluid.Source(createGoddessWaterProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> GODDESS_WATER_FLOWING = FLUIDS.register("goddess_water_flowing", () -> new BaseFlowingFluid.Flowing(createGoddessWaterProperties()));
    public static final DeferredHolder<Fluid, Fluid> ICY_WATER_STILL = FLUIDS.register("icy_water_still", () -> new BaseFlowingFluid.Source(createIcyWaterProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> ICY_WATER_FLOWING = FLUIDS.register("icy_water_flowing", () -> new BaseFlowingFluid.Flowing(createIcyWaterProperties()));

    private static BaseFlowingFluid.Properties createGoddessWaterProperties() {
        return new BaseFlowingFluid.Flowing.Properties(GODDESS_WATER_TYPE, GODDESS_WATER_STILL, GODDESS_WATER_FLOWING).block(HFBlocks.GODDESS_WATER).bucket(HFItems.GODDESS_WATER_BUCKET);
    }

    private static BaseFlowingFluid.Properties createIcyWaterProperties() {
        return new BaseFlowingFluid.Flowing.Properties(ICY_WATER_TYPE, ICY_WATER_STILL, ICY_WATER_FLOWING).block(HFBlocks.ICY_WATER).bucket(HFItems.ICY_WATER_BUCKET);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}

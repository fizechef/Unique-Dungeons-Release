package com.becks.uniquedungeons;

import com.becks.uniquedungeons.client.model.AmethystProjectileModel;
import com.becks.uniquedungeons.client.render.AmethystProjectileRenderer;
import com.becks.uniquedungeons.client.render.CrystalArrowRenderer;
import com.becks.uniquedungeons.client.render.SonicBoomProjectileRenderer;
import com.becks.uniquedungeons.common.artifact_unlock.ArtifactUnlockServer;
import com.becks.uniquedungeons.common.blocks.lockPedestal.LockPedestalRenderer;
import com.becks.uniquedungeons.common.blocks.pedestal.PedestalRenderer;
import com.becks.uniquedungeons.core.init.EntityInit;
import com.becks.uniquedungeons.core.init.PotionsInit;
import com.becks.uniquedungeons.core.init.structureInit.desert_dungeon.DesertDungeonInit;
import com.becks.uniquedungeons.core.init.structureInit.StructureInit;
import com.becks.uniquedungeons.core.init.structureInit.StructurePieceInit;
import com.becks.uniquedungeons.core.init.BlockEntityInit;
import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import com.becks.uniquedungeons.network.proxy.ClientProxy;
import com.becks.uniquedungeons.network.Network;
import com.becks.uniquedungeons.network.proxy.Proxy;
import com.becks.uniquedungeons.network.proxy.ServerProxy;
import com.becks.uniquedungeons.network.eventhandling.DamageEventHandler;
import com.becks.uniquedungeons.network.eventhandling.DeathEventHandler;
import com.becks.uniquedungeons.client.render.ClientRenderEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.becks.uniquedungeons.core.init.BlockInit;
import com.becks.uniquedungeons.core.init.itemInit.GeneralItemInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Hauptklasse des mods, regsitriert alle erstellten Objekte beim FML
 * @author erike
 *
 */
@Mod("uniquedungeons")
public class UniqueDungeons {
    public static final Logger LOGGER = LogManager.getLogger();
    
    // Mod Id String
    public static final String MOD_ID = "uniquedungeons";

    //Proxy for Client/Server side operations
    public static Proxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public UniqueDungeons() {
    	//Eventbusse zum Abfangen von Spiel Events und ausl�sen entsprechender Aktionen
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // Register the commonSetup method for modloading
        bus.addListener(this::commonSetup);
    	
    	//Registrieren der vom Mod erstellten Objekte beim forge modloader �ber den FMLModEventBus
    	GeneralItemInit.ITEMS.register(bus);
        ArtifactInit.ARTIFACTS.register(bus);
    	BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        PotionsInit.POTIONS.register(bus);
        EntityInit.ENTITIES.register(bus);
        StructureInit.STRUCTURE_TYPE.register(bus);
        StructurePieceInit.STRUCTURE_PIECES.register(bus);
        DesertDungeonInit.init();

    	//Listener f�r Events w�rend der Laufzeit des Spiels
    	DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.register(UniqueDungeons.ClientModEvents.class));
        forgeBus.register(this);
        forgeBus.register(DamageEventHandler.class);
        forgeBus.register(DeathEventHandler.class);


        Network.registerMessages();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        ArtifactInit.buildNameLookup();
        event.enqueueWork(
                () ->
                {
                    PotionBrewing.addMix(Potions.AWKWARD, Items.GLOW_BERRIES, PotionsInit.GLOW_POTION.get());
                }
        );
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
        ArtifactUnlockServer.getInstance().onServerStart();
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        ArtifactUnlockServer.getInstance().onServerStop();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    //@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            IEventBus forgeEventbus = MinecraftForge.EVENT_BUS;
            forgeEventbus.addListener(ClientRenderEventHandler::onHighlightBlockEvent);

            ItemBlockRenderTypes.setRenderLayer(BlockInit.CRYSTAL.get(), RenderType.translucent());

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            ItemPropertyFunction blockFunc = (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
            ItemPropertyFunction pullPercentFunc = (p174635, p174636, p174637, p174638) -> {
                if (p174637 == null) {
                    return 0.0F;
                } else {
                    return p174637.getUseItem() != p174635 ? 0.0F : (float)(p174635.getUseDuration() - p174637.getUseItemRemainingTicks()) / 20.0F;
                }
            };
            ItemPropertyFunction isPullingFunc = (p174630, p174631, p174632, p174633) -> p174632 != null && p174632.isUsingItem() && p174632.getUseItem() == p174630 ? 1.0F : 0.0F;

            ItemProperties.register(ArtifactInit.REFLECTING_SHIELD.get(), new ResourceLocation("minecraft:blocking"), blockFunc);
            ItemProperties.register(ArtifactInit.AMETHYST_SHIELD.get(), new ResourceLocation("minecraft:blocking"), blockFunc);
            ItemProperties.register(ArtifactInit.SONIC_BOW.get(), new ResourceLocation("minecraft:pull"), pullPercentFunc);
            ItemProperties.register(ArtifactInit.SONIC_BOW.get(), new ResourceLocation("minecraft:pulling"), isPullingFunc);

        }

        @SubscribeEvent
        public static void onRenderRegistry(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(BlockEntityInit.PEDESTAL.get(), ctx -> new PedestalRenderer());
            event.registerBlockEntityRenderer(BlockEntityInit.LOCK_PEDESTAL.get(), ctx -> new LockPedestalRenderer());
            event.registerEntityRenderer(EntityInit.AMETHYST_PROJECTILE.get(), AmethystProjectileRenderer::new);
            event.registerEntityRenderer(EntityInit.SONIC_BOOM_PROJECTILE.get(), SonicBoomProjectileRenderer::new);
            event.registerEntityRenderer(EntityInit.CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterRenderLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(AmethystProjectileModel.LAYER_LOCATION, AmethystProjectileModel::createBodyLayer);
        }
    }
}

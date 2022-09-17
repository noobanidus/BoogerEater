package studio.robotmonkey1000.boogereater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import studio.robotmonkey1000.boogereater.client.render.RenderBoogerEater;
import studio.robotmonkey1000.boogereater.common.configuration.ModConfigurations;
import studio.robotmonkey1000.boogereater.common.entity.EntityBoogerEater;
import studio.robotmonkey1000.boogereater.common.sound.BoogerSounds;



@Mod("boogereater")
public class BoogerMain {

	public static final String MOD_ID = "boogereater";
	public static final String NAME = "Booger Eater";
	public static final String VERSION = "1.0.0";
    private static final Logger LOGGER = LogManager.getLogger();
    
    //Entity Registry
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);


	
	public static final RegistryObject<EntityType<EntityBoogerEater>> BOOGEREATER = ENTITIES.register(
		    "boogereater", () -> EntityType.Builder.of(EntityBoogerEater::new, MobCategory.MONSTER).build("boogereater")
		);

	public BoogerMain() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        MinecraftForge.EVENT_BUS.register(this);

	}
	
	
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("IN SETUP");
        ModConfigurations.setupConfigs();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	
    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	
    	@SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        	LOGGER.info("Registering Entities");
        	DefaultAttributes.put(BOOGEREATER.get(), EntityBoogerEater.setCustomAttributes().build());	
        }
            
        @SubscribeEvent
        public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(BoogerSounds.SOUNDS.toArray(new SoundEvent[0]));
        }

        @SubscribeEvent
        public static void registerRenders(ModelRegistryEvent e) {
        	RenderingRegistry.registerEntityRenderingHandler(BOOGEREATER.get(), RenderBoogerEater::new);
        }

        @SubscribeEvent
        public static void onConfigChanged(ModConfigEvent event) {
            if (event.getConfig().getModId().equals(BoogerMain.MOD_ID)) {            
            }
            
        }
    }
}

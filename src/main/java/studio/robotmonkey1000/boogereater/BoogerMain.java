package studio.robotmonkey1000.boogereater;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import studio.robotmonkey1000.boogereater.common.configuration.ModConfigurations;
import studio.robotmonkey1000.boogereater.common.entity.EntityBoogerEater;


@Mod("boogereater")
public class BoogerMain {

  public static final String MOD_ID = "boogereater";
  public static final String NAME = "Booger Eater";
  public static final String VERSION = "1.0.0";
  private static final Logger LOGGER = LogManager.getLogger();

  //Entity Registry
  private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
  private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

  public static final RegistryObject<EntityType<EntityBoogerEater>> BOOGEREATER = ENTITIES.register(
      "boogereater", () -> EntityType.Builder.of(EntityBoogerEater::new, MobCategory.MONSTER).build("boogereater"));
  public static final RegistryObject<SoundEvent> HURT = SOUND_EVENTS.register("booger.eater.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "booger.eater.hurt")));
  public static final RegistryObject<SoundEvent> IDLE = SOUND_EVENTS.register("booger.eater.idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "booger.eater.idle")));
  public static final RegistryObject<SoundEvent> DEATH = SOUND_EVENTS.register("booger.eater.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "booger.eater.death")));

  public BoogerMain() {
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    ENTITIES.register(modBus);
    SOUND_EVENTS.register(modBus);
    modBus.addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);
  }


  private void setup(final FMLCommonSetupEvent event) {
    ModConfigurations.setupConfigs();
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void registerEntities(final EntityAttributeCreationEvent event) {
      event.put(BOOGEREATER.get(), EntityBoogerEater.setCustomAttributes().build());
    }
  }
}

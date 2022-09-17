package studio.robotmonkey1000.boogereater.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.client.render.RenderBoogerEater;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BoogerMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
  @SubscribeEvent
  public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(BoogerMain.BOOGEREATER.get(), RenderBoogerEater::new);
  }
}

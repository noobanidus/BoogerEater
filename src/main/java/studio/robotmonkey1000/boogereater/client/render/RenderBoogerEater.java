package studio.robotmonkey1000.boogereater.client.render;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.client.model.ModelBoogerEater;
import studio.robotmonkey1000.boogereater.common.entity.EntityBoogerEater;

public class RenderBoogerEater extends HumanoidMobRenderer<EntityBoogerEater, ModelBoogerEater> {

  private static final ResourceLocation TEXTURE = new ResourceLocation(BoogerMain.MOD_ID, "textures/entity/booger_eater/booger_eater.png");

  public RenderBoogerEater(EntityRendererProvider.Context context) {
    super(context, new ModelBoogerEater(context.bakeLayer(ModelLayers.ZOMBIE)), 1f);
  }

  public ResourceLocation getTextureLocation(EntityBoogerEater entity) {
    return TEXTURE;
  }

}
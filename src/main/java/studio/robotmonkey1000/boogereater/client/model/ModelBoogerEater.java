package studio.robotmonkey1000.boogereater.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.resources.ResourceLocation;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.common.entity.EntityBoogerEater;

public class ModelBoogerEater extends HumanoidModel<EntityBoogerEater> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(BoogerMain.MOD_ID, "textures/entity/booger_eater/booger_eater.png");
	private static final int textureSize = 64;
	
	public ModelBoogerEater() {
		super(0.5f, 1, textureSize, textureSize);
		this.young = true;
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer vertex, int i1, int i2, float f1, float f2, float f3, float f4) {
		super.renderToBuffer(matrix, vertex, i1, i2, f1, f2, f3, f4);
	}
	
	
	
	
	


}

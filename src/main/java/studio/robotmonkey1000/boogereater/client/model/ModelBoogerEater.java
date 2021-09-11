package studio.robotmonkey1000.boogereater.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.common.entity.EntityBoogerEater;

public class ModelBoogerEater extends BipedModel<EntityBoogerEater> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(BoogerMain.MOD_ID, "textures/entity/booger_eater/booger_eater.png");
	private static final int textureSize = 64;
	
	public ModelBoogerEater() {
		super(0.5f, 1, textureSize, textureSize);
		this.isChild = true;
	}
	
	@Override
	public void render(MatrixStack matrix, IVertexBuilder vertex, int i1, int i2, float f1, float f2, float f3, float f4) {
		super.render(matrix, vertex, i1, i2, f1, f2, f3, f4);
	}
	
	
	
	
	


}

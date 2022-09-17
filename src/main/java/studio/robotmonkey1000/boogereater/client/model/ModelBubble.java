package studio.robotmonkey1000.boogereater.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import studio.robotmonkey1000.boogereater.BoogerMain;

public class ModelBubble extends Model {
	public static final ResourceLocation TEXTURE = new ResourceLocation(BoogerMain.MOD_ID, "textures/entity/bubble/bubble.png");
	public final ModelRenderer Bubble;
	
	public ModelBubble() {
		super(RenderType::entitySolid);
		this.texWidth = 64;
		this.texHeight = 64;
		this.Bubble = new ModelRenderer(this, 64, 64);
		this.Bubble.setPos(0.0F, 16.0F, 0.0F);
		this.Bubble.addBox(-8.0F, -8.0F, -0.5F, 16, 16, 1, 0.0F);
//		this.Bubble.addBox(100, 100, 100, 100, 100, 100, 100);
		
		//super.accept(Bubble);

	}


	@Override
	public void renderToBuffer(MatrixStack matrix, IVertexBuilder vertex, int packedLight, int packedOverlay, float r, float g, float b, float a) {
//		this.Bubble.render(matrix, vertex, packedLight, packedOverlay, r, g, b, a);
		//matrix.push();
		this.Bubble.render(matrix, vertex, packedLight, packedOverlay);
		//matrix.pop();
	}
}

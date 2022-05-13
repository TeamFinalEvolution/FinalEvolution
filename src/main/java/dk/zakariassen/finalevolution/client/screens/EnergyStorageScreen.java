package dk.zakariassen.finalevolution.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import dk.zakariassen.finalevolution.network.messages.serverBound.ServerBoundTestBoxGetEnergyRequestMessage;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class EnergyStorageScreen extends BaseScreen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FinalEvolution.MOD_ID, "textures/gui/energy_storage.png");
    private static final int IMAGE_WIDTH = 176, IMAGE_HEIGHT = 168;
    private int leftPos = 0, topPos = 0;
    private final DimensionalEnergyStorageBlockEntity blockEntity;

    public EnergyStorageScreen(DimensionalEnergyStorageBlockEntity blockEntity) {
        super(TextComponent.EMPTY);
        this.blockEntity = blockEntity;

        FinalEvolutionNetwork.sendToServer(new ServerBoundTestBoxGetEnergyRequestMessage(blockEntity.getBlockPos()));
    }

    public int getEnergy() {
        return this.blockEntity.getEnergyStorage().getEnergyStored();
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        FinalEvolutionNetwork.sendToServer(new ServerBoundTestBoxGetEnergyRequestMessage(blockEntity.getBlockPos()));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.bindTexture();
        this.blit(stack, this.leftPos, this.topPos, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        
        final int energyStored = this.getEnergy();
        final int maxEnergy = this.blockEntity.getEnergyStorage().getMaxEnergyStored();
        final int scaledEnergy = (int) mapNumber(energyStored, 0, maxEnergy, 0, 122);
        this.bindTexture();
        this.blit(stack, this.leftPos + 59, this.topPos + 145 - scaledEnergy, 176, 122 - scaledEnergy, 58, scaledEnergy);
        
        this.font.draw(stack, this.title, this.leftPos + 7, this.topPos + 5, 0x404040);
        this.font.draw(stack, energyStored + "RF", this.leftPos + 10, this.topPos + 10, 0x404040);
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - IMAGE_WIDTH) / 2;
        this.topPos = (this.height - IMAGE_HEIGHT) / 2;
    }

    public void bindTexture() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
    }
    
    public static double mapNumber(double value, double rangeMin, double rangeMax, double resultMin, double resultMax) {
        return (value - rangeMin) / (rangeMax - rangeMin) * (resultMax - resultMin) + resultMin;
    }
}

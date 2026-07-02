package eu.seahousen.gregcasting.datagen;

import eu.seahousen.gregcasting.GCBlocks;
import eu.seahousen.gregcasting.GregCasting;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleBlockStateProvider extends BlockStateProvider {
    public SimpleBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // this.simpleBlockWithItem(GCBlocks.MEDIA_CASING.get(), models().cubeAll("machine_casing_media_proof", GregCasting.id("block/machine_casing_media_proof")));
    }
}

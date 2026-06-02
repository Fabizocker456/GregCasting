package eu.seahousen.gregcasting;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class GCBlocks {

    public static BlockEntry<Block> MEDIA_CASING = GregCasting.REGISTRATE.block("machine_casing_media_proof", Block::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static void init() {
        GregCasting.LOGGER.info("-- BLOCKS INITIALIZED --");
    }
}

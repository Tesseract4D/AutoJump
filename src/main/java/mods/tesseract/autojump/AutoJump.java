package mods.tesseract.autojump;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;

@Mod(modid = "autojump", acceptedMinecraftVersions = "[1.7.10]")
public class AutoJump extends CustomLoadingPlugin {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    }

    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    public void registerFixes() {
        registerClassWithFixes("mods.tesseract.autojump.fix.FixesEntityPlayerSP");
        registerSuperclassTransformer("net.minecraft.client.entity.EntityPlayerSP", "net.minecraft.client.entity.AbstractClientPlayer", "mods.tesseract.autojump.fix.EntityPlayerAJ");
    }
}

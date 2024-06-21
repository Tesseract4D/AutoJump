package mods.tesseract.autojump.fix;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.world.World;

public abstract class EntityPlayerAJ extends AbstractClientPlayer {
    public int autoJumpTime;
    public double lastX;
    public double lastZ;
    public EntityPlayerAJ(World w, GameProfile p) {
        super(w, p);
    }

}

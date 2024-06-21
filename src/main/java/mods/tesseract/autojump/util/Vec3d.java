package mods.tesseract.autojump.util;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Vec3d extends Vec3 {
    public Vec3d(double x, double y, double z) {
        super(x, y, z);
    }

    public Vec3d(Vec3 v) {
        this(v.xCoord, v.yCoord, v.zCoord);
    }

    public Vec3d add(Vec3d vec) {
        return this.add(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public Vec3d add(double x, double y, double z) {
        return new Vec3d(this.xCoord + x, this.yCoord + y, this.zCoord + z);
    }

    public double lengthSquared() {
        return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
    }

    public Vec3d scale(double factor) {
        return new Vec3d(this.xCoord * factor, this.yCoord * factor, this.zCoord * factor);
    }

    public static Vec3d fromPitchYaw(float x, float y) {
        float f = MathHelper.cos(-y * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-y * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-x * 0.017453292F);
        float f3 = MathHelper.sin(-x * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }
}

package mods.tesseract.autojump.fix;

import mods.tesseract.autojump.util.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

import java.util.List;


public class FixesEntityPlayerSP {
    public static double fastInvSqrt(double n) {
        double d0 = 0.5D * n;
        long i = Double.doubleToRawLongBits(n);
        i = 6910469410427058090L - (i >> 1);
        n = Double.longBitsToDouble(i);
        n = n * (1.5D - d0 * n * n);
        return n;
    }

    public static boolean intersects(AxisAlignedBB a, double x1, double y1, double z1, double x2, double y2, double z2) {
        return a.minX < x2 && a.maxX > x1 && a.minY < y2 && a.maxY > y1 && a.minZ < z2 && a.maxZ > z1;
    }

    public static boolean intersects(AxisAlignedBB a, Vec3 min, Vec3 max) {
        return intersects(a, Math.min(min.xCoord, max.xCoord), Math.min(min.yCoord, max.yCoord), Math.min(min.zCoord, max.zCoord), Math.max(min.xCoord, max.xCoord), Math.max(min.yCoord, max.yCoord), Math.max(min.zCoord, max.zCoord));
    }

    public static Vec3d getCenter(AxisAlignedBB a) {
        return new Vec3d(a.minX + (a.maxX - a.minX) * 0.5D, a.minY + (a.maxY - a.minY) * 0.5D, a.minZ + (a.maxZ - a.minZ) * 0.5D);
    }

    @Fix(insertOnExit = true)
    public static void func_145771_j(EntityPlayerSP c, double x, double y, double z) {
        EntityPlayerAJ p = (EntityPlayerAJ) (Object) c;
        if (p.autoJumpTime <= 0 && c.onGround && !c.isSneaking() && !c.isRiding()) {
            float mx = c.movementInput.moveStrafe, my = c.movementInput.moveForward;

            if (mx != 0.0F || my != 0.0F) {
                Vec3d vec3d = new Vec3d(c.posX, c.boundingBox.minY, c.posZ);
                double d0 = c.posX + c.motionX;
                double d1 = c.posZ + c.motionZ;
                Vec3d vec3d1 = new Vec3d(d0, c.boundingBox.minY, d1);
                Vec3d vec3d2 = new Vec3d(c.motionX, 0.0D, c.motionZ);
                float f = c.getAIMoveSpeed();
                float f1 = (float) vec3d2.lengthSquared();

                if (f1 <= 0.001F) {
                    float f2 = f * mx;
                    float f3 = f * my;
                    float f4 = MathHelper.sin(c.rotationYaw * 0.017453292F);
                    float f5 = MathHelper.cos(c.rotationYaw * 0.017453292F);
                    vec3d2 = new Vec3d(f2 * f5 - f3 * f4, vec3d2.yCoord, f3 * f5 + f2 * f4);
                    f1 = (float) vec3d2.lengthSquared();

                    if (f1 <= 0.001F) {
                        return;
                    }
                }

                float f12 = (float) fastInvSqrt(f1);
                Vec3d vec3d12 = vec3d2.scale(f12);
                Vec3d vec3d13 = Vec3d.fromPitchYaw(c.rotationPitch, c.rotationYaw);
                float f13 = (float) (vec3d13.xCoord * vec3d12.xCoord + vec3d13.zCoord * vec3d12.zCoord);

                if (f13 >= -0.15F) {
                    int bx = (int) c.posX, by = (int) c.boundingBox.maxY, bz = (int) c.posZ;
                    Block b = c.worldObj.getBlock(bx, by, bz);
                    if (b.getCollisionBoundingBoxFromPool(c.worldObj, bx, by, bz) == null) {
                        by++;
                        b = c.worldObj.getBlock(bx, by, bz);

                        if (b.getCollisionBoundingBoxFromPool(c.worldObj, bx, by, bz) == null) {
                            float f6 = 7.0F;
                            float f7 = 1.2F;

                            if (c.isPotionActive(Potion.jump)) {
                                f7 += (float) (c.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.75F;
                            }

                            float f8 = Math.max(f * 7.0F, 1.0F / f12);
                            Vec3d vec3d4 = vec3d1.add(vec3d12.scale(f8));
                            float f9 = c.width;
                            float f10 = c.height;
                            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, vec3d4.xCoord, vec3d4.yCoord + f10, vec3d4.zCoord));
                            axisalignedbb.maxX += f9;
                            axisalignedbb.minX -= f9;
                            axisalignedbb.maxZ += f9;
                            axisalignedbb.minZ -= f9;
                            Vec3d lvt_19_1_ = vec3d.add(0.0D, 0.5099999904632568D, 0.0D);
                            vec3d4 = vec3d4.add(0.0D, 0.5099999904632568D, 0.0D);
                            Vec3d vec3d5 = new Vec3d(-vec3d12.zCoord, 0, vec3d12.xCoord);
                            Vec3d vec3d6 = vec3d5.scale(f9 * 0.5F);
                            Vec3 vec3d7 = lvt_19_1_.subtract(vec3d6);
                            Vec3 vec3d8 = vec3d4.subtract(vec3d6);
                            Vec3d vec3d9 = lvt_19_1_.add(vec3d6);
                            Vec3d vec3d10 = vec3d4.add(vec3d6);
                            List<AxisAlignedBB> list = c.worldObj.getCollidingBoundingBoxes(c, axisalignedbb);

                            if (!list.isEmpty()) {
                            }

                            float f11 = Float.MIN_VALUE;
                            label86:
                            for (AxisAlignedBB axisalignedbb2 : list) {
                                if (intersects(axisalignedbb2, vec3d7, vec3d8) || intersects(axisalignedbb2, vec3d9, vec3d10)) {
                                    f11 = (float) axisalignedbb2.maxY;
                                    Vec3d vec3d11 = getCenter(axisalignedbb2);
                                    int bx1 = (int) vec3d11.xCoord, by1 = (int) vec3d11.yCoord, bz1 = (int) vec3d11.zCoord;
                                    int i = 1;

                                    while (true) {
                                        if ((float) i >= f7) {
                                            break label86;
                                        }

                                        int by2 = by + 1;
                                        Block b2 = c.worldObj.getBlock(bx1, by2, bz1);
                                        AxisAlignedBB axisalignedbb1;

                                        if ((axisalignedbb1 = b2.getCollisionBoundingBoxFromPool(c.worldObj, bx1, by2, bz1)) != null) {
                                            f11 = (float) axisalignedbb1.maxY + by2;

                                            if ((double) f11 - c.boundingBox.minY > (double) f7) {
                                                return;
                                            }
                                        }

                                        if (i > 1) {
                                            by++;
                                            Block b3 = c.worldObj.getBlock(bx, by, bz);

                                            if (b3.getCollisionBoundingBoxFromPool(c.worldObj, bx, by, bz) != null) {
                                                return;
                                            }
                                        }

                                        ++i;
                                    }
                                }
                            }

                            if (f11 != Float.MIN_VALUE) {
                                float f14 = (float) ((double) f11 - c.boundingBox.minY);

                                if (f14 > 0.5F && f14 <= f7) {
                                    p.autoJumpTime = 1;
                                    System.out.println(114514);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

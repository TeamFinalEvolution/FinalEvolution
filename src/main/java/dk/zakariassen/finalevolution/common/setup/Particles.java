package dk.zakariassen.finalevolution.common.setup;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;

public class Particles {
    public static final Vector3f ENDER_PARTICLE_COLOR = new Vector3f(new Vec3(44, 205, 177));
    public static final DustParticleOptions ENDER = new DustParticleOptions(ENDER_PARTICLE_COLOR, 1.0F);
}

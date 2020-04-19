package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagDouble;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityFiroBall extends EntityFlying implements ISpawnable {

    public EntityFiroBall(World world) {
        super(world);
        lifeSpan = 300;
        life = lifeSpan;
        b(0.9F, 0.9F);
        sinage = new float[3];
        fireProof = true;
        for(int i = 0; i < 3; i++)
            sinage[i] = random.nextFloat() * 6F;
    }

    public EntityFiroBall(World world, double x, double y, double z, boolean flag) {
        super(world);
        lifeSpan = 300;
        life = lifeSpan;
        b(0.9F, 0.9F);
        setLocation(x, y, z, yaw, pitch);
        sinage = new float[3];
        fireProof = true;
        for(int i = 0; i < 3; i++)
            sinage[i] = random.nextFloat() * 6F;
        smotionX = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        smotionY = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        smotionZ = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        if(flag) {
            frosty = true;
            smotionX /= 3D;
            smotionY = 0.0D;
            smotionZ /= 3D;
        }
    }

    public EntityFiroBall(World world, double x, double y, double z, boolean flag, boolean flag2) {
        super(world);
        lifeSpan = 300;
        life = lifeSpan;
        b(0.9F, 0.9F);
        setLocation(x, y, z, yaw, pitch);
        sinage = new float[3];
        fireProof = true;
        for(int i = 0; i < 3; i++)
            sinage[i] = random.nextFloat() * 6F;
        smotionX = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        smotionY = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        smotionZ = (0.20000000000000001D + (double)random.nextFloat() * 0.14999999999999999D) * (random.nextInt(2) != 0 ? -1D : 1.0D);
        if(flag) {
            frosty = true;
            smotionX /= 3D;
            smotionY = 0.0D;
            smotionZ /= 3D;
        }
        fromCloud = flag2;
    }
    
    @Override
    public void m_() {
        super.m_();
        life--;
        if(life <= 0)
            dead = true;
    }

    public void updateAnims() {
        if (!frosty)
            for(int i = 0; i < 3; i++) {
                sinage[i] += 0.3F + (float)i * 0.13F;
                if(sinage[i] > 6.283186F)
                    sinage[i] -= 6.283186F;
            }
    }
    
    @Override
    public void c_() {
        motX = smotionX;
        motY = smotionY;
        motZ = smotionZ;
        if (bd) {
            if(frosty && smacked) {
                dead = true;
            } else {
                int i = MathHelper.floor(locX);
                int j = MathHelper.floor(boundingBox.b);
                int k = MathHelper.floor(locZ);
                if(smotionX > 0.0D && world.getTypeId(i + 1, j, k) != 0)
                    motX = smotionX = -smotionX;
                else if(smotionX < 0.0D && world.getTypeId(i - 1, j, k) != 0)
                    motX = smotionX = -smotionX;
                if(smotionY > 0.0D && world.getTypeId(i, j + 1, k) != 0)
                    motY = smotionY = -smotionY;
                else if(smotionY < 0.0D && world.getTypeId(i, j - 1, k) != 0)
                    motY = smotionY = -smotionY;
                if(smotionZ > 0.0D && world.getTypeId(i, j, k + 1) != 0)
                    motZ = smotionZ = -smotionZ;
                else if(smotionZ < 0.0D && world.getTypeId(i, j, k - 1) != 0)
                    motZ = smotionZ = -smotionZ;
            }
        }
        velocityChanged = true;
        updateAnims();
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("LifeLeft", (short)life);
        nbttagcompound.a("SeriousKingVampire", a(new double[] {
            smotionX, smotionY, smotionZ
        }));
        nbttagcompound.a("Frosty", frosty);
        nbttagcompound.a("FromCloud", fromCloud);
        nbttagcompound.a("Smacked", smacked);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        life = nbttagcompound.d("LifeLeft");
        frosty = nbttagcompound.m("Frosty");
        fromCloud = nbttagcompound.m("FromCloud");
        smacked = nbttagcompound.m("Smacked");
        NBTTagList nbttaglist = nbttagcompound.l("SeriousKingVampire");
        smotionX = (float)((NBTTagDouble)nbttaglist.a(0)).a;
        smotionY = (float)((NBTTagDouble)nbttaglist.a(1)).a;
        smotionZ = (float)((NBTTagDouble)nbttaglist.a(2)).a;
    }
    
    @Override
    public void collide(Entity entity) {
        super.collide(entity);
        boolean flag = false;
        if(entity != null && (entity instanceof EntityLiving) && !(entity instanceof EntityFiroBall)) {
            if(frosty /*&& (!(entity instanceof EntityFireMonster) || smacked && !fromCloud) && !(entity instanceof EntityFireMinion)*/)
                flag = entity.damageEntity(this, 5);
            else if(!frosty /*&& !(entity instanceof EntityFireMonster) && !(entity instanceof EntityFireMinion)*/) {
                flag = entity.damageEntity(this, 5);
                if(flag)
                    entity.fireTicks = 100;
            }
        }
        if(flag)
            dead = true;
    }
    
    @Override
    public boolean damageEntity(Entity entity, int i) {
        if(entity != null) {
            Vec3D vec3d = entity.Z();
            if(vec3d != null) {
                smotionX = vec3d.a;
                smotionY = vec3d.b;
                smotionZ = vec3d.c;
            }
            smacked = true;
            return true;
        } else
            return false;
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

	@Override
	public Packet230ModLoader getSpawnPacket() {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.dataInt = new int[] {id, frosty ? 1 : 0, fromCloud ? 1 : 0};
		packet.dataFloat = new float[] {(float) locX, (float) locY, (float) locZ, (float) smotionX, (float) smotionY, (float) smotionZ};
		return packet;
	}
	
    public float sinage[];
    public double smotionX;
    public double smotionY;
    public double smotionZ;
    public int life;
    public int lifeSpan;
    public boolean frosty;
    public boolean smacked;
    public boolean fromCloud;
}

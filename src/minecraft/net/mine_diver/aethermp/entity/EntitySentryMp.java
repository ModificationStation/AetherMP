package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySentry;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySentryMp extends EntitySentry
{

    public EntitySentryMp(World world)
    {
        super(world);
    }
    
    @Override
    public void func_100019_e(int i)
    {
        super.func_100019_e(i);
        dataWatcher.addObject(16, 0);
        dataWatcher.addObject(17, 0);
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("LostYou", dataWatcher.getWatchableObjectInt(16));
        nbttagcompound.setBoolean("Active", dataWatcher.getWatchableObjectInt(17) == 1);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        dataWatcher.updateObject(16, nbttagcompound.getInteger("LostYou"));
        dataWatcher.updateObject(17, nbttagcompound.getBoolean("Active") ? 1 : 0);
    }
    
    @Override
    public void onUpdate()
    {
        boolean flag = onGround;
        super.onUpdate();
        if(onGround && !flag)
        {
            worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        } else
        if(!onGround && flag && playerToAttack != null)
        {
            motionX *= 3D;
            motionZ *= 3D;
        }
        if(playerToAttack != null && playerToAttack.isDead)
        {
            playerToAttack = null;
        }
        if (worldObj.multiplayerWorld){
    		if(dataWatcher.getWatchableObjectInt(16) >= 4)
            {
                shutdown();
            }
        	if(dataWatcher.getWatchableObjectInt(16) < 4)
            {
                texture = "/aether/mobs/SentryLit.png";
            }
        }
    }
    
    @Override
    public void setEntityDead()
    {
    	if (worldObj.multiplayerWorld)
    		isDead = true;
		super.setEntityDead();
    }
    
    @Override
    public boolean attackEntityFrom(Entity entity, int i)
    {
        boolean flag = super.attackEntityFrom(entity, i);
        if(flag && (entity instanceof EntityLiving))
        {
            dataWatcher.updateObject(17, 1);
            dataWatcher.updateObject(16, 0);
            playerToAttack = entity;
            texture = "/aether/mobs/SentryLit.png";
        }
        return flag;
    }
    
    @Override
    public void shutdown()
    {
        counter = -64;
        dataWatcher.updateObject(17, 0);
        playerToAttack = null;
        texture = "/aether/mobs/Sentry.png";
        setPathToEntity(null);
        moveStrafing = 0.0F;
        moveForward = 0.0F;
        isJumping = false;
        motionX = 0.0D;
        motionZ = 0.0D;
    }
    
    @Override
    protected void updatePlayerActionState()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 8D);
        if(dataWatcher.getWatchableObjectInt(17) == 0 && counter >= 8)
        {
            if(entityplayer != null && canEntityBeSeen(entityplayer))
            {
                faceEntity(entityplayer, 10F, 10F);
                playerToAttack = entityplayer;
                dataWatcher.updateObject(17, 1);
                dataWatcher.updateObject(16, 0);
                texture = "/aether/mobs/SentryLit.png";
            }
            counter = 0;
        } else
        if(dataWatcher.getWatchableObjectInt(17) == 1 && counter >= 8)
        {
            if(playerToAttack == null)
            {
                if(entityplayer != null && canEntityBeSeen(entityplayer))
                {
                    playerToAttack = entityplayer;
                    dataWatcher.updateObject(17, 1);
                    dataWatcher.updateObject(16, 0);
                } else
                {
                	dataWatcher.updateObject(16, dataWatcher.getWatchableObjectInt(16)+1);
                    if(dataWatcher.getWatchableObjectInt(16) >= 4)
                    {
                        shutdown();
                    }
                }
            } else
            if(!canEntityBeSeen(playerToAttack) || getDistanceToEntity(playerToAttack) >= 16F)
            {
                dataWatcher.updateObject(16, dataWatcher.getWatchableObjectInt(16)+1);
                if(dataWatcher.getWatchableObjectInt(16) >= 4)
                {
                    shutdown();
                }
            } else
            {
                dataWatcher.updateObject(16, 0);
            }
            counter = 0;
        } else
        {
            counter++;
        }
        if(dataWatcher.getWatchableObjectInt(17) == 0)
        {
            return;
        }
        if(playerToAttack != null)
        {
            faceEntity(playerToAttack, 10F, 10F);
        }
        if(onGround && jcount-- <= 0)
        {
            jcount = rand.nextInt(20) + 10;
            isJumping = true;
            moveStrafing = 0.5F - rand.nextFloat();
            moveForward = 1.0F;
            worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            if(playerToAttack != null)
            {
                jcount /= 2;
                moveForward = 1.0F;
            }
        } else
        {
            isJumping = false;
            if(onGround)
            {
                moveStrafing = moveForward = 0.0F;
            }
        }
    }

    public float field_100021_a;
    public float field_100020_b;
    private int jcount;
    public int size;
    public int counter;
}

package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.NameGen;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Tool;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;

public class EntitySlider extends EntityFlying implements IAetherBoss {

    public EntitySlider(World world) {
        super(world);
        yaw = 0.0F;
        pitch = 0.0F;
        b(2.0F, 2.0F);
        health = 500;
        dennis = 1;
        texture = "/aether/mobs/sliderSleep.png";
        chatTime = 60;
    }
    
    @Override
    public void b() {
        super.b();
        locX = Math.floor(locX + 0.5D);
        locY = Math.floor(locY + 0.5D);
        locZ = Math.floor(locZ + 0.5D);
        datawatcher.a(16, (byte) 0);
        datawatcher.a(17, health);
        datawatcher.a(18, NameGen.gen());
    }
    
    @Override
    public boolean h_() {
        return false;
    }
    
    @Override
    protected String g() {
        return "ambient.cave.cave";
    }
    
    @Override
    protected String i() {
        return "step.stone";
    }
    
    @Override
    protected String h() {
        return "aether.sound.bosses.slider.sliderDeath";
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Speedy", speedy);
        nbttagcompound.a("MoveTimer", (short)moveTimer);
        nbttagcompound.a("Direction", (short)direction);
        nbttagcompound.a("GotMovement", gotMovement);
        nbttagcompound.a("Awake", awake);
        nbttagcompound.a("DungeonX", dungeonX);
        nbttagcompound.a("DungeonY", dungeonY);
        nbttagcompound.a("DungeonZ", dungeonZ);
        boolean current = target instanceof EntityPlayer ? isCurrentBoss((EntityPlayer) target) : false;
        nbttagcompound.a("IsCurrentBoss", current);
        if (current)
        	nbttagcompound.setString("TargetNickname", ((EntityPlayer)target).name);
        nbttagcompound.setString("BossName", datawatcher.c(18));
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        datawatcher.watch(17, health);
        speedy = nbttagcompound.g("Speedy");
        moveTimer = nbttagcompound.d("MoveTimer");
        direction = nbttagcompound.d("Direction");
        gotMovement = nbttagcompound.m("GotMovement");
        awake = nbttagcompound.m("Awake");
        dungeonX = nbttagcompound.e("DungeonX");
        dungeonY = nbttagcompound.e("DungeonY");
        dungeonZ = nbttagcompound.e("DungeonZ");
        if(nbttagcompound.m("IsCurrentBoss")) {
        	EntityPlayer player = ((WorldServer)world).server.serverConfigurationManager.i(nbttagcompound.getString("TargetNickname"));
        	if (player != null) {
	            target = player;
	            PlayerManager.setCurrentBoss(player, this);
        	}
        }
        datawatcher.watch(18, nbttagcompound.getString("BossName"));
        if(awake) {
            if(criticalCondition()) {
                texture = "/aether/mobs/sliderAwake_red.png";
                datawatcher.watch(16, (byte) 2);
            } else {
                texture = "/aether/mobs/sliderAwake.png";
                datawatcher.watch(16, (byte) 1);
            }
        }
    }

    public boolean criticalCondition() {
        return health <= 125;
    }
    
    @Override
    public void m_() {
        super.m_();
        K = pitch = yaw = 0.0F;
        if(awake) {
            if(target != null && (target instanceof EntityLiving)) {
                EntityLiving e1 = (EntityLiving)target;
                if(e1.health <= 0) {
                    awake = false;
                    if (target instanceof EntityPlayer)
                    	PlayerManager.setCurrentBoss((EntityPlayer) target, null);
                    target = null;
                    texture = "/aether/mobs/sliderSleep.png";
                    datawatcher.watch(16, (byte) 0);
                    stop();
                    openDoor();
                    moveTimer = 0;
                    return;
                }
            } else {
                if(target != null && target.dead) {
                    awake = false;
                    if (target instanceof EntityPlayer)
                    	PlayerManager.setCurrentBoss((EntityPlayer) target, null);
                    target = null;
                    texture = "/aether/mobs/sliderSleep.png";
                    datawatcher.watch(16, (byte) 0);
                    stop();
                    openDoor();
                    moveTimer = 0;
                    return;
                }
                if(target == null) {
                    target = world.findNearbyPlayer(this, -1D);
                    if(target == null) {
                        awake = false;
                        //mod_Aether.currentBoss = null;
                        target = null;
                        texture = "/aether/mobs/sliderSleep.png";
                        datawatcher.watch(16, (byte) 0);
                        stop();
                        openDoor();
                        moveTimer = 0;
                        return;
                    }
                }
            }
            if(gotMovement) {
                if(bd) {
                    double x = locX - 0.5D;
                    double y = boundingBox.b + 0.75D;
                    double z = locZ - 0.5D;
                    crushed = false;
                    if(y < 124D && y > 4D) {
                        if(direction == 0)
                            for(int i = 0; i < 25; i++)
                            {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x + a), (int)(y + 1.5D), (int)(z + b));
                            }
                        else if(direction == 1)
                            for(int i = 0; i < 25; i++)
                            {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x + a), (int)(y - 1.5D), (int)(z + b));
                            }
                        else if(direction == 2)
                            for(int i = 0; i < 25; i++)
                            {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x + 1.5D), (int)(y + a), (int)(z + b));
                            }
                        else if(direction == 3)
                            for(int i = 0; i < 25; i++)
                            {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x - 1.5D), (int)(y + a), (int)(z + b));
                            }
                        else if(direction == 4)
                            for(int i = 0; i < 25; i++)
                            {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x + a), (int)(y + b), (int)(z + 1.5D));
                            }
                        else if(direction == 5)
                            for(int i = 0; i < 25; i++) {
                                double a = (double)(i / 5 - 2) * 0.75D;
                                double b = (double)(i % 5 - 2) * 0.75D;
                                blockCrush((int)(x + a), (int)(y + b), (int)(z - 1.5D));
                            }
                    }
                    if(crushed) {
                        PacketManager.makeSound((float) locX, (float) locY, (float) locZ, "random.explode", 3F, (0.625F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, ((WorldServer) world).dimension);
                        PacketManager.makeSound(this, "aether.sound.bosses.slider.sliderCollide", 2.5F, 1.0F / (random.nextFloat() * 0.2F + 0.9F));
                    }
                    stop();
                } else {
                    if(speedy < 2.0F)
                        speedy += criticalCondition() ? 0.0325F : 0.025F;
                    motX = 0.0D;
                    motY = 0.0D;
                    motZ = 0.0D;
                    if(direction == 0) {
                        motY = speedy;
                        if(boundingBox.b > target.boundingBox.b + 0.34999999999999998D) {
                            stop();
                            moveTimer = 8;
                        }
                    } else if(direction == 1) {
                        motY = -speedy;
                        if(boundingBox.b < target.boundingBox.b - 0.25D) {
                            stop();
                            moveTimer = 8;
                        }
                    } else if(direction == 2) {
                        motX = speedy;
                        if(locX > target.locX + 0.125D) {
                            stop();
                            moveTimer = 8;
                        }
                    } else if(direction == 3) {
                        motX = -speedy;
                        if(locX < target.locX - 0.125D) {
                            stop();
                            moveTimer = 8;
                        }
                    } else if(direction == 4) {
                        motZ = speedy;
                        if(locZ > target.locZ + 0.125D) {
                            stop();
                            moveTimer = 8;
                        }
                    } else if(direction == 5) {
                        motZ = -speedy;
                        if(locZ < target.locZ - 0.125D) {
                            stop();
                            moveTimer = 8;
                        }
                    }
                }
            } else if(moveTimer > 0) {
                moveTimer--;
                if(criticalCondition() && random.nextInt(2) == 0)
                    moveTimer--;
                motX = 0.0D;
                motY = 0.0D;
                motZ = 0.0D;
            } else {
                double a = Math.abs(locX - target.locX);
                double b = Math.abs(boundingBox.b - target.boundingBox.b);
                double c = Math.abs(locZ - target.locZ);
                if(a > c) {
                    direction = 2;
                    if(locX > target.locX)
                        direction = 3;
                } else {
                    direction = 4;
                    if(locZ > target.locZ)
                        direction = 5;
                }
                if(b > a && b > c || b > 0.25D && random.nextInt(5) == 0) {
                    direction = 0;
                    if(locY > target.locY)
                        direction = 1;
                }
                PacketManager.makeSound(this, "aether.sound.bosses.slider.sliderMove", 2.5F, 1.0F / (random.nextFloat() * 0.2F + 0.9F));
                gotMovement = true;
            }
        }
        if(harvey > 0.01F)
            harvey *= 0.8F;
        if(chatTime > 0)
            chatTime--;
    }

    private void openDoor() {
        int x = dungeonX + 15;
        for(int y = dungeonY + 1; y < dungeonY + 5; y++)
            for(int z = dungeonZ + 6; z < dungeonZ + 10; z++)
                world.setRawTypeId(x, y, z, 0);
    }
    
    @Override
    public void collide(Entity entity) {
        if(awake && gotMovement) {
            boolean flag = entity.damageEntity(this, 6);
            if(flag && (entity instanceof EntityLiving)) {
            	PacketManager.makeSound(this, "aether.sound.bosses.slider.sliderCollide", 2.5F, 1.0F / (random.nextFloat() * 0.2F + 0.9F));
                if((entity instanceof EntityCreature) || (entity instanceof EntityHuman)) {
                    EntityLiving ek = (EntityLiving)entity;
                    ek.motY += 0.34999999999999998D;
                    ek.motX *= 2D;
                    ek.motZ *= 2D;
                    ek.velocityChanged = true;
                }
                stop();
            }
        }
    }
    
    @Override
    protected void q() {
        for(int i = 0; i < 7 + random.nextInt(3); i++)
            b(BlockManager.DungeonStone.id, 1);
        a(new ItemStack(ItemManager.Key, 1, 0), 0.0F);
    }
    
    @Override
    public boolean d() {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return world.getTypeId(i, j - 1, k) == Block.GRASS.id && world.k(i, j, k) > 8 && super.d();
    }

    public void stop() {
        gotMovement = false;
        moveTimer = 12;
        direction = 0;
        speedy = 0.0F;
        motX = 0.0D;
        motY = 0.0D;
        motZ = 0.0D;
    }

    private void chatItUp(EntityPlayer entityPlayer, String s) {
        if(chatTime <= 0) {
        	entityPlayer.a(s);
            chatTime = 60;
        }
    }
    
    @Override
    public boolean damageEntity(Entity e1, int i) {
        if(e1 == null || !(e1 instanceof EntityHuman))
            return false;
        EntityHuman p1 = (EntityHuman)e1;
        ItemStack stack = p1.G();
        if(stack == null || stack.getItem() == null)
            return false;
        if(stack.getItem() instanceof Tool) {
            Tool tool = (Tool)stack.getItem();
            if(!tool.canHarvest(Block.STONE)) {
            	if (p1 instanceof EntityPlayer)
            		chatItUp((EntityPlayer) p1, "Hmm. Perhaps I need to attack it with a Pickaxe?");
                return false;
            }
        } else {
        	if (p1 instanceof EntityPlayer)
        		chatItUp((EntityPlayer) p1, "Hmm. Perhaps I need to attack it with a Pickaxe?");
            return false;
        }
        boolean flag = super.damageEntity(e1, Math.max(0, i));
        if(flag) {
        	datawatcher.watch(17, health);
            if(health <= 0)
            {
                dead = true;
                openDoor();
                unlockBlock(dungeonX, dungeonY, dungeonZ);
                world.setRawTypeIdAndData(dungeonX + 7, dungeonY + 1, dungeonZ + 7, Block.TRAP_DOOR.id, 3);
                world.setRawTypeIdAndData(dungeonX + 8, dungeonY + 1, dungeonZ + 7, Block.TRAP_DOOR.id, 2);
                world.setRawTypeIdAndData(dungeonX + 7, dungeonY + 1, dungeonZ + 8, Block.TRAP_DOOR.id, 3);
                world.setRawTypeIdAndData(dungeonX + 8, dungeonY + 1, dungeonZ + 8, Block.TRAP_DOOR.id, 2);
                if (target instanceof EntityPlayer) {
                	Achievements.giveAchievement(Achievements.defeatBronze, (EntityPlayer) target);
                	PlayerManager.setCurrentBoss((EntityPlayer) target, null);
                }
            } else
            if(!awake) {
            	PacketManager.makeSound(this, "aether.sound.bosses.slider.sliderAwaken", 2.5F, 1.0F / (random.nextFloat() * 0.2F + 0.9F));
                awake = true;
                target = e1;
                texture = "/aether/mobs/sliderAwake.png";
                datawatcher.watch(16, (byte) 1);
                int x = dungeonX + 15;
                for(int y = dungeonY + 1; y < dungeonY + 8; y++)
                    for(int z = dungeonZ + 5; z < dungeonZ + 11; z++)
                        world.setRawTypeId(x, y, z, BlockManager.LockedDungeonStone.id);
                if (p1 instanceof EntityPlayer)
                	PlayerManager.setCurrentBoss((EntityPlayer) p1, this);
            } else if(gotMovement)
                speedy *= 0.75F;
            double a = Math.abs(locX - e1.locX);
            double c = Math.abs(locZ - e1.locZ);
            if(a > c) {
                dennis = 1;
                rennis = 0;
                if(locX > e1.locX)
                    dennis = -1;
            } else {
                rennis = 1;
                dennis = 0;
                if(locZ > e1.locZ)
                    rennis = -1;
            }
            harvey = 0.7F - (float)health / 875F;
            if(criticalCondition()) {
                texture = "/aether/mobs/sliderAwake_red.png";
                datawatcher.watch(16, (byte) 2);
            } else {
                texture = "/aether/mobs/sliderAwake.png";
                datawatcher.watch(16, (byte) 1);
            }
        }
        return flag;
    }

    private void unlockBlock(int i, int j, int k) {
        int id = world.getTypeId(i, j, k);
        if(id == BlockManager.LockedDungeonStone.id) {
            world.setRawTypeIdAndData(i, j, k, BlockManager.DungeonStone.id, world.getData(i, j, k));
            unlockBlock(i + 1, j, k);
            unlockBlock(i - 1, j, k);
            unlockBlock(i, j + 1, k);
            unlockBlock(i, j - 1, k);
            unlockBlock(i, j, k + 1);
            unlockBlock(i, j, k - 1);
        }
        if(id == BlockManager.LockedLightDungeonStone.id) {
            world.setRawTypeIdAndData(i, j, k, BlockManager.LightDungeonStone.id, world.getData(i, j, k));
            unlockBlock(i + 1, j, k);
            unlockBlock(i - 1, j, k);
            unlockBlock(i, j + 1, k);
            unlockBlock(i, j - 1, k);
            unlockBlock(i, j, k + 1);
            unlockBlock(i, j, k - 1);
        }
    }
    
    @Override
    public void b(double d3, double d4, double d5) {}
    
    @Override
    public void a(Entity entity1, int j, double d2, double d3) {}

    public void blockCrush(int x, int y, int z) {
        int a = world.getTypeId(x, y, z);
        int b = world.getData(x, y, z);
        if(a == 0 || a == BlockManager.LockedDungeonStone.id || a == BlockManager.LockedLightDungeonStone.id)
            return;
        PacketManager.addBlockDestroyEffects(x, y, z, a, b, ((WorldServer) world).dimension);
        Block.byId[a].remove(world, x, y, z);
        Block.byId[a].g(world, x, y, z, b);
        world.setTypeId(x, y, z, 0);
        crushed = true;
        addSquirrelButts(x, y, z);
    }
    
    public void addSquirrelButts(int x, int y, int z) {
    	Packet230ModLoader packet = new Packet230ModLoader();
    	packet.packetType = 11;
    	packet.dataInt = new int[] {getBossEntityID(), x, y, z};
    	PacketManager.sendToViewDistance(packet, ((WorldServer) world).dimension, x, y, z);
    }

    public void setDungeon(int i, int j, int k) {
        dungeonX = i;
        dungeonY = j;
        dungeonZ = k;
    }

    @Override
    public int getBossHP() {
        return health;
    }

    @Override
    public int getBossMaxHP() {
        return 500;
    }

    @Override
    public boolean isCurrentBoss(EntityPlayer entityPlayer) {
    	IAetherBoss boss = PlayerManager.getCurrentBoss(entityPlayer);
        if(boss == null)
            return false;
        else
            return equals(boss);
    }

    @Override
    public int getBossEntityID() {
        return id;
    }
    
    @Override
    public String getBossTitle() {
        return (new StringBuilder()).append(datawatcher.c(18)).append(", the Slider").toString();
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    public int moveTimer;
    public int dennis;
    public int rennis;
    public int chatTime;
    public Entity target;
    public boolean awake;
    public boolean gotMovement;
    public boolean crushed;
    public float speedy;
    public float harvey;
    public int direction;
    private int dungeonX;
    private int dungeonY;
    private int dungeonZ;
}

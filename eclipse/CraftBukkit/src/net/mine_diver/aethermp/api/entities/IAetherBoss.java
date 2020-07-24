package net.mine_diver.aethermp.api.entities;

import net.minecraft.server.EntityPlayer;

public interface IAetherBoss {

    public int getBossHP();

    public int getBossMaxHP();

    public boolean isCurrentBoss(EntityPlayer entityPlayer);

    public int getBossEntityID();

    public String getBossTitle();
}

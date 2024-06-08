package dev.demon.utils.entity.v1_8;

import com.mojang.authlib.GameProfile;
import dev.demon.AntiESP;
import dev.demon.user.User;
import dev.demon.utils.entity.CustomEntity;
import dev.demon.utils.entity.EntityData;
import dev.demon.utils.location.CustomLocation;
import dev.demon.utils.math.MathUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityHook18 extends CustomEntity {

    private EntityPlayer playerESP;
    private EntityData.EntityType entityType;

    public EntityHook18(User user, UUID uuid, EntityData creationData) {
        super(user, uuid, creationData);
    }

    @Override
    public void removeEntity(User user) {
        setActive(false);

        switch (this.entityType) {
            case DEFAULT: {
                if (this.playerESP == null) return;
                sendPacket(new PacketPlayOutEntityDestroy(this.playerESP.getId()), user);
                break;
            }
        }
    }

    @Override
    public void tickEntity(User user, CustomLocation customLocation, boolean onGround) {

        switch (this.entityType) {
            case DEFAULT: {
                if (this.playerESP == null) return;

                MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();

                playerESP.onGround = user.getTick() % 20 == 0;

                playerESP.setInvisible(true);

                playerESP.setHealth((float) MathUtil
                        .getRandomDouble(MathUtil.getRandomDouble(5.32, 0.0), 20.0));

                playerESP.setSneaking(user.getTick() % 5 == 0);

                playerESP.setSprinting(false);

                if (!playerESP.onGround) {
                    playerESP.setAirTicks((int) MathUtil
                            .getRandomDouble(10, 1));
                }

                playerESP.velocityChanged = false;

                playerESP.playerConnection = new PlayerConnection(minecraftServer,
                        new NetworkManager(EnumProtocolDirection.CLIENTBOUND), playerESP);

                int chance = MathUtil.getRandomInteger(0, 100);
                int hurtTimeRandom = MathUtil.getRandomInteger(9, 1);

                playerESP.hurtTicks = chance < 10 ? hurtTimeRandom : 0;

                customLocation.setPitch(customLocation.getPitch()
                        + MathUtil.getRandomFloat(90F, -90F));

                customLocation.setYaw(customLocation.getYaw()
                        + MathUtil.getRandomFloat(360f, -360f));

                if (Math.abs(customLocation.getPitch()) > 90.0F) {
                    customLocation.setPitch(90.0F);
                }

                sendPacket(new PacketPlayOutEntityHeadRotation(this.playerESP,
                        (byte) (customLocation.getYaw() * 256 / 360)), user);
                this.playerESP.setLocation(customLocation.getPosX(), customLocation.getPosY(), customLocation.getPosZ(),
                        customLocation.getYaw(), customLocation.getPitch());

                setLastReportedLocation(customLocation);

                sendPacket(new PacketPlayOutEntityTeleport(this.playerESP), user);

                break;
            }
        }

        this.setLastUpdateTick(user.getTick());
    }

    @Override
    public void createEntity(User user, EntityData.EntityType entityType, EntityData entityData) {
        WorldServer worldServer = ((CraftWorld) user.getPlayer().getWorld()).getHandle();

        setCreationData(entityData);

        switch (entityType) {

            case DEFAULT: {

                Player randomPlayer = getRandomPlayer(user);

                if (randomPlayer == null) {
                    return;
                }

                String name = randomPlayer.getName();

                MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();

                EntityPlayer entityPlayer = new EntityPlayer(minecraftServer, worldServer,
                        new GameProfile(UUID.fromString(String.valueOf(randomPlayer.getUniqueId())), name),
                        new PlayerInteractManager(worldServer));


                entityPlayer.onGround = false;

                entityPlayer.playerInteractManager.b(WorldSettings.EnumGamemode.SURVIVAL);
                entityPlayer.setInvisible(true);

                entityPlayer.setCustomNameVisible(false);

                entityPlayer.setHealth((float) MathUtil
                        .getRandomDouble(MathUtil.getRandomDouble(5.32, 0.0), 20.0));

                entityPlayer.setSneaking(true);

                entityPlayer.setSprinting(false);

                if (!entityPlayer.onGround) {
                    entityPlayer.setAirTicks((int) MathUtil
                            .getRandomDouble(10, 1));
                }

                entityPlayer.velocityChanged = false;

                entityPlayer.ping = ((CraftPlayer) randomPlayer).getHandle().ping;

                entityPlayer.playerConnection = new PlayerConnection(minecraftServer,
                        new NetworkManager(EnumProtocolDirection.CLIENTBOUND), entityPlayer);

                entityPlayer.setLocation(entityData.getSpawnLocation().getPosX(), entityData.getSpawnLocation().getPosY(),
                        entityData.getSpawnLocation().getPosZ(),
                        entityData.getSpawnLocation().getYaw(), entityData.getSpawnLocation().getPitch());

                sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer), user);
                sendPacket(new PacketPlayOutEntityTeleport(entityPlayer), user);
                sendPacket(new PacketPlayOutUpdateAttributes(), user);

                setEntityID(entityPlayer.getId());
                setLastReportedLocation(entityData.getSpawnLocation());

                user.getCurrentSpawnedEntites().add(getEntityID());
                this.playerESP = entityPlayer;

                break;
            }
        }

        this.entityType = entityType;
    }

    private void sendPacket(Packet packet, User user) {
        AntiESP.getInstance().getInstanceManager().getInstance().sendPacket(packet, user);
    }

    private static Player getRandomPlayer(User user) {
        Player randomPlayer = null;

        try {
            if (Bukkit.getServer().getOnlinePlayers().size() > 1) {
                List<Player> onlinePlayers = new ArrayList<>();

                for (Player online : user.getPlayer().getWorld().getPlayers()) {
                    if (online.getUniqueId().toString().equalsIgnoreCase(user.getPlayer().getUniqueId().toString()))
                        continue;
                    onlinePlayers.add(online);
                }

                randomPlayer = onlinePlayers.get(new Random().nextInt(onlinePlayers.size()));
            } else {
                randomPlayer = user.getPlayer();
            }
        } catch (Exception ignored) {}

        return randomPlayer;
    }
}

package dev.demon.bukkit;

import dev.demon.AntiESP;
import dev.demon.user.User;
import dev.demon.utils.entity.EntityData;
import dev.demon.utils.location.CustomLocation;
import dev.demon.utils.math.MathUtil;
import dev.demon.utils.stream.StreamUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    public BukkitListener() {
        Bukkit.getPluginManager().registerEvents(this, AntiESP.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        AntiESP.getInstance().getUserManager().add(event.getPlayer());

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(final PlayerQuitEvent event) {
        AntiESP.getInstance().getUserManager().remove(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        this.processEvent(event);
    }

    void processEvent(Event event) {
        AntiESP.getInstance().getExecutorService().execute(() -> this.process(event));
    }

    void process(Event event) {
        if (event instanceof PlayerMoveEvent) {
            User user = AntiESP.getInstance().getUserManager().get(((PlayerMoveEvent) event).getPlayer());

            if (user == null) return;

            user.setTick(user.getTick() + 1);

            if (!user.isESP()) {
                return;
            }

            Player player = user.getPlayer();

            Location location = player.getLocation();

            if (location == null) return;

            if (user.isEntitiesCreated()) {

                if (user.getLastUpdate() == -1337L || (System.currentTimeMillis() - user.getLastUpdate()) > 2500L) {
                    user.setLastUpdate(System.currentTimeMillis());

                    StreamUtil.filter(user.getEntityHelper1_8().getCustomEntities(), customEntity -> customEntity.
                                    getCreationData().getEntityType() == EntityData.EntityType.DEFAULT)
                            .forEach(customEntity -> customEntity.tickEntity(user,
                                    new CustomLocation(location.getX() +
                                            MathUtil.getRandomDouble(
                                                    customEntity.getCreationData().getOffsetX() - 30,
                                                    customEntity.getCreationData().getOffsetX() + 30),
                                            location.getY() + MathUtil.getRandomDouble(
                                                    customEntity.getCreationData().getOffsetY() - 30,
                                                    customEntity.getCreationData().getOffsetY() + 30),
                                            location.getZ() + MathUtil.getRandomDouble(
                                                    customEntity.getCreationData().getOffsetZ() - 30,
                                                    customEntity.getCreationData().getOffsetZ() + 30)),
                                    true));
                }
            } else {
                for (int i = 0; i < 25; i++) {
                    double angle = 2 * Math.PI * i / 50;
                    double x = 50 * Math.cos(angle);
                    double y = location.getY();
                    double z = -3 * Math.sin(angle);

                    createEntity(user, location.getY(),
                            location.getX(),
                            location.getZ(), x, y, z);

                    user.setEntitiesCreated(true);
                }
            }
        }
    }

    private void createEntity(User user,
                              double y, double x, double z, double offsetX, double offsetY, double offsetZ) {

        EntityData entityData = new EntityData();

        entityData.setEntityType(EntityData.EntityType.DEFAULT);

        entityData.setSpawnLocation(new CustomLocation(x + offsetX, y + offsetY, z + offsetZ));
        entityData.setPostLocation(new CustomLocation(x + offsetX, y + offsetY, z + offsetZ));
        entityData.setOffsetX(offsetX);
        entityData.setOffsetY(offsetY);
        entityData.setOffsetZ(offsetZ);


        user.getEntityHelper1_8().getCustomEntities().add(
                user.getEntityHelper1_8().lastEntityBot = ((AntiESP.getInstance().getEntityManager()
                        .createEntity(EntityData.EntityType.DEFAULT, user, entityData))));
    }
}
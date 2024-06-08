package dev.demon.user;

import dev.demon.utils.entity.v1_8.EntityHelper1_8;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class User {

    private Player player;
    private UUID uuid;

    private int tick;
    private int amount = 25;

    private double maxDistance = 30, minDistance = 30;

    private long lastUpdate = -1337L;

    public EntityHelper1_8 entityHelper1_8;

    private final List<Integer> currentSpawnedEntites = new CopyOnWriteArrayList<>();

    private boolean isESP = true;

    private boolean entitiesCreated = false;

    public User(final Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();

        this.entityHelper1_8 = new EntityHelper1_8();
    }

}
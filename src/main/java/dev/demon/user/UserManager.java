package dev.demon.user;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
public class UserManager {

    private final Map<UUID, User> dataMap = new ConcurrentHashMap<>();

    public void add(final Player player) {

        final UUID uuid = player.getUniqueId();

        if (!dataMap.containsKey(uuid)) {
            final User data = new User(player);
            dataMap.put(uuid, data);
        }
    }

    public void remove(final Player player) {
        final UUID uuid = player.getUniqueId();
        dataMap.remove(uuid);
    }

    public User get(final Player player) {
        return dataMap.get(player.getUniqueId());
    }
}

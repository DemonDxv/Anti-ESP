package dev.demon;

import dev.demon.bukkit.BukkitListener;
import dev.demon.command.CommandManager;
import dev.demon.user.UserManager;
import dev.demon.utils.entity.EntityManager;
import dev.demon.utils.nms.InstanceManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
@Setter
public class AntiESP extends JavaPlugin {

    @Getter
    private static AntiESP instance;

    private final UserManager userManager = new UserManager();

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final EntityManager entityManager = new EntityManager();

    private InstanceManager instanceManager = new InstanceManager();

    private CommandManager commandManager = new CommandManager();

    private final String version = "1.0-SNAPSHOT";


    @Override
    public void onEnable() {
        instance = this;

        this.runListener();

        this.instanceManager.create();
    }


    //TODO: remove entities...
    @Override
    public void onDisable() {

    }

    public void runListener() {
        new BukkitListener();
    }
}

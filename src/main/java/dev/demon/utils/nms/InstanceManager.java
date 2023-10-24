package dev.demon.utils.nms;

import dev.demon.utils.nms.instances.Instance1_7_R4;
import dev.demon.utils.nms.instances.Instance1_8_R3;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class InstanceManager {
    private Instance instance;
    public final String version = Bukkit.getServer().getClass().getPackage().getName()
            .replace(".", ",").split(",")[3];


    public void create() {

        if (version.equalsIgnoreCase("v1_7_R4")) {
            this.instance = new Instance1_7_R4();
        } else if (version.equalsIgnoreCase("v1_8_R3")) {
            this.instance = new Instance1_8_R3();
        } else {
            this.instance = null;
        }
    }
}

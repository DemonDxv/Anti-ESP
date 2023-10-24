package dev.demon.utils.nms.instances;


import dev.demon.user.User;
import dev.demon.utils.nms.Instance;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

@SuppressWarnings("rawtypes")
public class Instance1_8_R3 extends Instance {

    @Override
    public void sendPacket(Object packet, User user) {
        ((CraftPlayer) user.getPlayer()).getHandle().playerConnection.sendPacket((Packet) packet);
    }
}

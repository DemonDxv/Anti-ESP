
package dev.demon.utils.entity.v1_8;

import dev.demon.utils.entity.CustomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class EntityHelper1_8 {

    public EntityPlayer entityPlayer;
    public int entityID;
    public CustomEntity lastEntityBot;

    private final List<CustomEntity> customEntities = new CopyOnWriteArrayList<>();
}

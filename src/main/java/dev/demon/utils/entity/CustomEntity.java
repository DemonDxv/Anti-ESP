package dev.demon.utils.entity;

import dev.demon.user.User;
import dev.demon.utils.location.CustomLocation;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class CustomEntity extends AbstractEntity {

    private final User user;
    private final UUID uuid;

    @Setter
    private EntityData creationData;

    @Setter
    private boolean active = true;

    @Setter
    private int lastUpdateTick;

    public CustomEntity(User user, UUID uuid, EntityData creationData) {
        this.user = user;
        this.uuid = uuid;
        this.creationData = creationData;
    }

    @Setter
    private int entityID;

    @Setter
    private CustomLocation lastReportedLocation;

    @Override
    public void createEntity(User user, EntityData.EntityType entityType, EntityData entityData) {

    }

    @Override
    public void removeEntity(User user) {

    }

    @Override
    public void tickEntity(User user, CustomLocation customLocation, boolean onGround) {

    }
}

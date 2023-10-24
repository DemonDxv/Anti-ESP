package dev.demon.utils.entity;

import dev.demon.user.User;
import dev.demon.utils.location.CustomLocation;

public abstract class AbstractEntity {

    public abstract void createEntity(User user, EntityData.EntityType entityType, EntityData entityData);

    public abstract void removeEntity(User user);

    public abstract void tickEntity(User user, CustomLocation customLocation, boolean onGround);
}

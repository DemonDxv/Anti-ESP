package dev.demon.utils.entity;

import dev.demon.utils.location.CustomLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EntityData {
    private CustomLocation spawnLocation, postLocation;
    private EntityType entityType;
    private double offsetX, offsetY, offsetZ;

    public enum EntityType {
        DEFAULT,
    }
}

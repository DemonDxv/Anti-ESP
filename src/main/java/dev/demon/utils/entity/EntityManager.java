package dev.demon.utils.entity;

import dev.demon.user.User;
import dev.demon.utils.entity.v1_8.EntityHook18;
import dev.demon.utils.stream.StreamUtil;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager {

    @Getter
    private final Map<UUID, CustomEntity> entityMap = new ConcurrentHashMap<>();

    public CustomEntity getEntity(int id) {

        for (CustomEntity customEntity : this.entityMap.values()) {
            if (customEntity.getEntityID() != id || !customEntity.isActive()) continue;

            return customEntity;
        }

        return null;
    }

    public void removeAll(User user) {

        if (user == null || user.getEntityHelper1_8() == null || user.getCurrentSpawnedEntites() == null) return;

        user.getEntityHelper1_8().getCustomEntities().forEach(customEntity -> {
            customEntity.removeEntity(user);

            user.getCurrentSpawnedEntites().stream().filter(integer -> integer ==
                    customEntity.getEntityID()).forEach(integer ->
                    user.getCurrentSpawnedEntites().remove(integer));

            this.entityMap.remove(customEntity.getUuid());
            user.getEntityHelper1_8().getCustomEntities().remove(customEntity);
        });
    }

    public void remove(User user, EntityData.EntityType entityType) {
        StreamUtil.filter(user.getEntityHelper1_8().getCustomEntities(), customEntity ->
                customEntity.getCreationData().getEntityType() == entityType).forEach(customEntity -> {
            customEntity.removeEntity(user);

            user.getCurrentSpawnedEntites().stream().filter(integer -> integer ==
                    customEntity.getEntityID()).forEach(integer ->
                    user.getCurrentSpawnedEntites().remove(integer));

            this.entityMap.remove(customEntity.getUuid());
            user.getEntityHelper1_8().getCustomEntities().remove(customEntity);
        });
    }

    public CustomEntity getEntity(UUID uuid) {
        return this.entityMap.get(uuid);
    }

    public CustomEntity createEntity(EntityData.EntityType entityType, User user, EntityData entityData) {
        UUID randomUUID = UUID.randomUUID();

        CustomEntity customEntity = new EntityHook18(user, randomUUID, entityData);

        customEntity.createEntity(user, entityType, entityData);

        this.entityMap.put(randomUUID, customEntity);
        return customEntity;
    }
}

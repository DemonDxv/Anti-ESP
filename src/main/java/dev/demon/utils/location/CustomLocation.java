package dev.demon.utils.location;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

@Getter @Setter
public class CustomLocation {
    private int tick;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private World world;
    private long timeStamp;

    public CustomLocation() {
    }

    public void setFlyingLocation(CustomLocation flyingLocation) {
        this.posX = flyingLocation.posX;
        this.posY = flyingLocation.posY;
        this.posZ = flyingLocation.posZ;
        this.yaw = flyingLocation.yaw;
        this.pitch = flyingLocation.pitch;
        this.world = flyingLocation.getWorld();
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(Location flyingLocation) {
        this.posX = flyingLocation.getX();
        this.posY = flyingLocation.getY();
        this.posZ = flyingLocation.getZ();
        this.yaw = flyingLocation.getYaw();
        this.pitch = flyingLocation.getPitch();
        this.world = flyingLocation.getWorld();
        this.timeStamp = System.currentTimeMillis();
    }
    public CustomLocation(double d0, double d1, double d2) {
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(int tick, double posX, double posY, double posZ) {
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(int tick, double posX, double posY, double posZ, float yaw, float pitch) {
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(World world,double posX, double posY, double posZ) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(World world, int tick, double posX, double posY, double posZ) {
        this.world = world;
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(World world, double posX, double posY, double posZ, float yaw, float pitch) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(double posX, double posY, double posZ, float yaw, float pitch) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(World world, int tick, double posX, double posY, double posZ, float yaw, float pitch,
                          boolean onGround) {
        this.world = world;
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.timeStamp = System.currentTimeMillis();
    }

    public Vector toVector() {
        return new Vector(this.posX, this.posY, this.posZ);
    }


    public Location toLocation(World world) {
        return new Location(world, this.posX, this.posY, this.posZ, this.yaw, this.pitch);
    }

    public CustomLocation add(double x, double y, double z) {
        return new CustomLocation(this.tick, this.posX + x, this.posY + y,
                this.posZ + z, this.yaw, this.pitch);
    }

    public CustomLocation clone() {
        return new CustomLocation(
                this.world,
                this.tick,
                this.posX,
                this.posY,
                this.posZ,
                this.yaw,
                this.pitch,
                this.onGround
        );
    }

    public double distanceSquaredXZ(CustomLocation o) {
        if (o.getWorld() != null && getWorld() != null && o.getWorld() == getWorld()) {
            return NumberConversions.square(this.getPosX() - o.getPosX())
                    + NumberConversions.square(this.getPosZ() - o.getPosZ());
        }
        return 0.0;
    }

    public String toString() {
        return "[" + this.posX + ", " + this.posY + ", " + this.posZ + "]";
    }
}

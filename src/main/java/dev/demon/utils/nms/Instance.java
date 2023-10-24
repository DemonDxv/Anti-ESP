package dev.demon.utils.nms;


import dev.demon.user.User;

public abstract class Instance {

    public abstract void sendPacket(Object packet, User data);
}


package dev.demon.command.commands.sub;


import dev.demon.AntiESP;
import dev.demon.user.User;
import dev.demon.utils.entity.EntityData;
import dev.demon.utils.stream.StreamUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ToggleCommand {

    public void execute(String[] args, String s, CommandSender commandSender) {
        User user = AntiESP.getInstance().getUserManager().get(((Player) commandSender));

        if (user != null) {

            if (user.isESP()) {

                AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) -> {
                    user1.setESP(false);
                    user1.setEntitiesCreated(false);
                });

                AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) ->
                        StreamUtil.filter(user1.getEntityHelper1_8().getCustomEntities(), customEntity -> customEntity.
                                getCreationData().getEntityType() == EntityData.EntityType.DEFAULT)
                        .forEach(customEntity -> customEntity.removeEntity(user1)));


                commandSender.sendMessage(ChatColor.RED + "Anti-ESP have been toggled off!");
            } else {
                AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) -> user1.setESP(true));

                commandSender.sendMessage(ChatColor.GREEN + "Anti-ESP have been toggled on!");
            }
        } else {
            commandSender.sendMessage("If you see this message the ign Dvm0n in game or demonpvp#7922 on discord.");
        }
    }
}

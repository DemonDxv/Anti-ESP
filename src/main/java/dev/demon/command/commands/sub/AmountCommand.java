package dev.demon.command.commands.sub;


import dev.demon.AntiESP;
import dev.demon.user.User;
import dev.demon.utils.entity.EntityData;
import dev.demon.utils.stream.StreamUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AmountCommand {

    public void execute(String[] args, String s, CommandSender commandSender) {
        User user = AntiESP.getInstance().getUserManager().get(((Player) commandSender));

        if (user != null) {

            if (args.length >= 1) {

                if (args[1] != null) {
                    int amount = Integer.parseInt(args[1]);
                    user.setAmount(amount);

                    AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) -> {
                        user1.setESP(false);
                        user1.setEntitiesCreated(false);
                    });

                    AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) ->
                            StreamUtil.filter(user1.getEntityHelper1_8().getCustomEntities(), customEntity -> customEntity.
                                            getCreationData().getEntityType() == EntityData.EntityType.DEFAULT)
                                    .forEach(customEntity -> customEntity.removeEntity(user1)));

                    AntiESP.getInstance().getUserManager().getDataMap().forEach((uuid, user1) -> user1.setESP(true));

                    commandSender.sendMessage(ChatColor.GREEN + "Set amount to: " + amount);

                } else {
                    commandSender.sendMessage("Usage: /antiesp amount (bot-amount)");
                }
            } else {
                commandSender.sendMessage("Usage: /antiesp amount (bot-amount)");
            }
        } else {
            commandSender.sendMessage("If you see this message the ign Dvm0n in game or demonpvp#7922 on discord.");
        }
    }
}

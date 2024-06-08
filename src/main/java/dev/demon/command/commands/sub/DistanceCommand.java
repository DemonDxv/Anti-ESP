package dev.demon.command.commands.sub;


import dev.demon.AntiESP;
import dev.demon.user.User;
import dev.demon.utils.entity.EntityData;
import dev.demon.utils.stream.StreamUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DistanceCommand {

    public void execute(String[] args, String s, CommandSender commandSender) {
        User user = AntiESP.getInstance().getUserManager().get(((Player) commandSender));

        if (user != null) {

            if (args.length >= 2) {

                if (args[1] != null && args[2] != null) {
                    double max = Double.parseDouble(args[1]);
                    double min = Double.parseDouble(args[2]);

                    user.setMaxDistance(max);
                    user.setMinDistance(min);

                    commandSender.sendMessage(ChatColor.GREEN + "Set max distance to: " + max);
                    commandSender.sendMessage(ChatColor.GREEN + "Set min distance to: " + min);

                } else {
                    commandSender.sendMessage("Usage: /antiesp distance (max) (min)");
                }
            } else {
                commandSender.sendMessage("Usage: /antiesp distance (max) (min)");
            }
        } else {
            commandSender.sendMessage("If you see this message the ign Dvm0n in game or demonpvp#7922 on discord.");
        }
    }
}

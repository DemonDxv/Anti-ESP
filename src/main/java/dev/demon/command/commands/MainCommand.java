package dev.demon.command.commands;

import dev.demon.AntiESP;
import dev.demon.command.commands.sub.ToggleCommand;
import dev.demon.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MainCommand extends BukkitCommand {

    private final String line = ChatColor.GRAY + "§m------------------------------------------";

    private final ToggleCommand toggleCommand = new ToggleCommand();

    public MainCommand(String name) {
        super(name);
        this.description = "Main anticheat command.";
        this.usageMessage = "/" + name;
        this.setAliases(new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("antiesp")) {

            if (commandSender instanceof Player || commandSender instanceof ConsoleCommandSender) {

                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;

                    User user = AntiESP.getInstance().getUserManager().get(player);

                    if (user != null) {

                        if (player.hasPermission("antiesp.command")
                                || player.isOp()) {

                            if (args.length < 1) {
                                commandSender.sendMessage(ChatColor.GOLD + "AntiESP" + ChatColor.GRAY + " - "
                                        + ChatColor.RED + AntiESP.getInstance().getVersion());
                                commandSender.sendMessage(line);

                                AntiESP.getInstance().getCommandManager().getCommandList().forEach(command -> {

                                    TextComponent textComponent = new TextComponent(ChatColor.GRAY + "» " + ChatColor.WHITE
                                            + "/" + command.getCommand() + ChatColor.GRAY + " - " + ChatColor.RED
                                            + command.getDescription());
                                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            new ComponentBuilder((command.getUsage() != null ? ChatColor.RED + command.getUsage()
                                                    : ChatColor.WHITE + "No usage found.")).create()));
                                    player.spigot().sendMessage(textComponent);
                                });

                                commandSender.sendMessage(line);
                            } else {
                                String s = args[0];
                                boolean found = false;
                                if (s.equalsIgnoreCase("toggle") &&
                                        player.hasPermission("antiesp.command.toggle")) {
                                    found = true;
                                    toggleCommand.execute(args, s, commandSender);
                                } else if (s.equalsIgnoreCase("mode") &&
                                        player.hasPermission("antiesp.command.mode")) {
                                //    found = true;
                                  //  forceBanCommand.execute(args, s, commandSender);
                                }

                                if (!found) commandSender.sendMessage(ChatColor.RED + "Sub command doesn't exist!");
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.RED + "You don't have permission to execute this command.");
                        }
                    }
                }
            }
        }
        return false;
    }
}
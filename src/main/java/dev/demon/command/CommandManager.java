package dev.demon.command;


import dev.demon.command.commands.MainCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandManager {
    private final List<Command> commandList = new ArrayList<>();

    private final String commandName = "antiesp";

    public CommandManager() {

        addCommand(new Command(new MainCommand(commandName),
                commandName,
                null,
                "Main command.", true));

        addCommand(new Command(new MainCommand(commandName),
                commandName + " toggle",
                "/" + commandName + " toggle",
                "Toggle on, and off anti esp.", true));

        addCommand(new Command(new MainCommand(commandName),
                commandName + " distance",
                "/" + commandName + " distance (max) (min)",
                "Set the distance.", true));

        addCommand(new Command(new MainCommand(commandName),
                commandName + " amount",
                "/" + commandName + " amount (bot-amount)",
                "Set the amount of bots around the player.", true));


    /*    addCommand(new Command(new MainCommand(commandName),
                commandName + " mode",
                "/" + commandName + " mode",
                "Changes the mode of anti-esp.", true));*/

    }

    private void addCommand(Command... commands) {
        for (Command command : commands) {
            commandList.add(command);
            if (command.isEnabled()) CommandUtils.registerCommand(command);
        }
    }

    public void removeCommand() {
        commandList.forEach(CommandUtils::unRegisterBukkitCommand);
    }
}


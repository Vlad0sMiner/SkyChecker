package ru.Vlad0sMiner.classes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import static ru.Vlad0sMiner.classes.Main.*;

public class RemoveAccount implements CommandExecutor {
    public Main plugin;

    public RemoveAccount(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if((sender instanceof ConsoleCommandSender) && (args.length > 0) && (args[0] != null)) {
            plugin.getRegistrationsBase().set("accounts." + args[0].toLowerCase(), null);
            plugin.saveRegistersBase();
            sender.sendMessage(prefix + account_removed);
        }
        return true;
    }
}
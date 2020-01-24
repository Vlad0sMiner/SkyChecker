package ru.Vlad0sMiner.classes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static ru.Vlad0sMiner.classes.Main.*;

public class ConsoleInput implements CommandExecutor {
    public Main plugin;

    public ConsoleInput(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            if ((sender instanceof ConsoleCommandSender) && (args.length > 0) && (args[0] != null) && (args[1] != null)) {
                plugin.getRegistrationsBase().set("accounts." + args[0].toLowerCase(), args[1]);
                plugin.saveRegistersBase();
                System.out.println(prefix + success_create_account + args[0].toLowerCase() + " " + args[1]);
            }
        } catch (Exception e) {
            System.out.println(prefix + error_input + e);
        }
        if (sender instanceof Player) {
            sender.sendMessage(prefix + only_console_command);
        }
        return true;
    }
}
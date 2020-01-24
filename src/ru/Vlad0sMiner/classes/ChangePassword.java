package ru.Vlad0sMiner.classes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static ru.Vlad0sMiner.classes.Main.*;

public class ChangePassword implements CommandExecutor {
    public Main plugin;

    public ChangePassword(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            if ((sender instanceof Player) && (args.length > 0) && (args[0] != null) && (args[1] != null)) {
                if (args[0].equals(args[1])) {
                    sender.sendMessage(prefix  + password_change_duplicate);
                } else {
                    if (args[0].equals(plugin.getRegistrationsBase().getString("accounts." + sender.getName().toLowerCase()))) {
                        plugin.getRegistrationsBase().set("accounts." + sender.getName().toLowerCase(), args[1]);
                        plugin.saveRegistersBase();
                        sender.sendMessage(prefix + change_password_success);
                    } else sender.sendMessage(prefix + password_change_failed);
                }
            }
        } catch (Exception e) {
            sender.sendMessage(prefix + password_change_empty);
        }
        return true;
    }
}
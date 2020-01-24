package ru.Vlad0sMiner.classes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ru.Vlad0sMiner.classes.Main.*;

public class NewAccount implements CommandExecutor {
    public Main plugin;
    public String password;

    public NewAccount(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if ((sender.hasPermission("skymine.checker") || sender.hasPermission("*") || sender.isOp()) &&
                (plugin.getRegistrationsBase().getString("accounts." + sender.getName().toLowerCase()) == null)) {
            if (args.length > 0) {
                if (args[0] != null) {
                    password = args[0];
                    plugin.getRegistrationsBase().set("accounts." + sender.getName().toLowerCase(), password);
                    plugin.saveRegistersBase();

                    if (login.contains(sender.getName().toLowerCase())) {
                        sender.sendMessage(prefix + already_autorised);
                    } else {
                        login.add(sender.getName().toLowerCase());
                        sender.sendMessage(prefix + success_register);
                    }
                } else {
                    sender.sendMessage(prefix + wrong_password);
                }
            }
        } else {
            sender.sendMessage(prefix + unknown_error);
        }
        return true;
    }
}

package ru.Vlad0sMiner.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static ru.Vlad0sMiner.classes.Main.*;

public class Timer extends BukkitRunnable {

    Main plugin;

    public Timer(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if ((p.hasPermission("skymine.checker") || p.hasPermission("*") || p.isOp()) &&
                    (!login.contains(p.getName().toLowerCase())) &&
                    (plugin.getRegistrationsBase().getString("accounts." + p.getName().toLowerCase()) != null)) {
                p.sendMessage(prefix + login_timer_msg);
            } else if ((p.hasPermission("skymine.checker") || p.hasPermission("*") || p.isOp()) &&
                    (!login.contains(p.getName().toLowerCase())) &&
                    (plugin.getRegistrationsBase().getString("accounts." + p.getName().toLowerCase()) == null)) {
                p.sendMessage(prefix + reg_timer_msg);
            }
        }
    }

}
package ru.Vlad0sMiner.classes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import static ru.Vlad0sMiner.classes.Main.*;

public class Login implements Listener, CommandExecutor {
    public String password;
    public Main plugin;

    public Login(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if ((sender.hasPermission("skymine.checker") || sender.hasPermission("*") || sender.isOp()) &&
                (plugin.getRegistrationsBase().getString("accounts." + sender.getName().toLowerCase())) != null) {
            this.password = plugin.getRegistrationsBase().getString("accounts." + sender.getName().toLowerCase());
            if ((args.length == 0) && (plugin.getRegistrationsBase().getString("accounts." + sender.getName().toLowerCase())) != null) {
                sender.sendMessage(prefix + login_mess);
            } else if (args.length > 0) {
                if (args[0].equals(password)) {
                    if (Main.login.contains(sender.getName().toLowerCase())) {
                        sender.sendMessage(prefix + already_autorised);
                    } else {
                        Main.login.add(sender.getName().toLowerCase());
                        sender.sendMessage(prefix + login_success);
                    }
                } else {
                    sender.sendMessage(prefix + wrong_password);
                }
            } else {
                sender.sendMessage(prefix + empty_reg);
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void Command(final PlayerCommandPreprocessEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp()) &&
                (plugin.getRegistrationsBase().getString("accounts." + event.getPlayer().getName().toLowerCase())) != null) {
            final String[] mess2 = event.getMessage().split(" ");
            this.password = plugin.getRegistrationsBase().getString("accounts." + event.getPlayer().getName().toLowerCase());
            try {
                if (mess2.length >= 1 && mess2[0].equalsIgnoreCase("/l") && mess2[1].equals(password)) {
                    event.setCancelled(false);
                } else {
                    event.getPlayer().sendMessage(prefix + wrong_password);
                    event.setCancelled(true);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                event.getPlayer().sendMessage(prefix + unknown_password_entered);
            }
        } else {
            final String[] mess2 = event.getMessage().split(" ");
            try {
                if ((!login.contains(event.getPlayer().getName().toLowerCase())) &&
                        (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())
                        && mess2.length >= 1 && mess2[0].equalsIgnoreCase("/reg") && mess2[1] != null) {
                    event.setCancelled(false);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                event.getPlayer().sendMessage(prefix + unknown_password_entered);
            }

        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            login.remove(event.getPlayer().getName().toLowerCase());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onBreak(final BlockBreakEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onPlace(final BlockPlaceEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void AsyncPlayerChatEvent(final AsyncPlayerChatEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onMove(final PlayerMoveEvent event) {
        if (!login.contains(event.getPlayer().getName().toLowerCase()) &&
                (event.getPlayer().hasPermission("skymine.checker") || event.getPlayer().hasPermission("*") || event.getPlayer().isOp())) {
            event.setCancelled(true);
        }
    }
}

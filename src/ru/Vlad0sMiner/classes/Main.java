package ru.Vlad0sMiner.classes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class Main extends JavaPlugin implements Listener {
    public static ArrayList<String> login = new ArrayList<>();

    //Начало переменных содержащих сообщения
    public static String prefix;
    public static String login_mess;
    public static String login_timer_msg;
    public static String reg_timer_msg;
    public static String register;
    public static String success_register;
    public static String login_success;
    public static String empty_reg;
    public static String wrong_password;
    public static String already_autorised;
    public static String change_password_success;
    public static String change_password;
    public static String password_change_failed;
    public static String password_change_duplicate;
    public static String password_change_empty;
    public static String account_removed;
    public static String only_console_command;
    public static String error_input;
    public static String success_create_account;
    public static String unknown_error;
    public static String unknown_password_entered;
    //Конец

    Timer login_msg;

    private File registrations;
    private FileConfiguration registrationsBase;

    private File messages;
    private FileConfiguration messagesConfig;

    public static String color(String format){
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public void messagesLoader(){
        prefix = (color(getMessagesConfig().getString("prefix")));
        login_mess = (color(getMessagesConfig().getString("login")));
        login_timer_msg = (color(getMessagesConfig().getString("login_timer_msg")));
        reg_timer_msg = (color(getMessagesConfig().getString("reg_timer_msg")));
        register = (color(getMessagesConfig().getString("register")));
        success_register = (color(getMessagesConfig().getString("success_register")));
        login_success = (color(getMessagesConfig().getString("login_success")));
        empty_reg = (color(getMessagesConfig().getString("empty_reg")));
        wrong_password = (color(getMessagesConfig().getString("wrong_password")));
        already_autorised = (color(getMessagesConfig().getString("already_autorised")));
        change_password_success = (color(getMessagesConfig().getString("change_password_success")));
        change_password = (color(getMessagesConfig().getString("change_password")));
        password_change_failed = (color(getMessagesConfig().getString("password_change_failed")));
        password_change_duplicate = (color(getMessagesConfig().getString("password_change_duplicate")));
        password_change_empty = (color(getMessagesConfig().getString("password_change_empty")));
        account_removed = (color(getMessagesConfig().getString("account_removed")));
        only_console_command = (color(getMessagesConfig().getString("only_console_command")));
        error_input = (color(getMessagesConfig().getString("error_input")));
        success_create_account = (color(getMessagesConfig().getString("success_create_account")));
        unknown_error = (color(getMessagesConfig().getString("unknown_error")));
        unknown_password_entered = (color(getMessagesConfig().getString("unknown_password_entered")));
    }

    public FileConfiguration getRegistrationsBase() {
        return registrationsBase;
    }

    private void createRegistrationsBase() {
        registrations = new File(getDataFolder(), "registrations.yml");
        if (!registrations.exists()) {
            registrations.getParentFile().mkdirs();
            saveResource("registrations.yml", false);
        }
        registrationsBase = new YamlConfiguration();
        try {
            registrationsBase.load(registrations);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    private void createMessagesConfig() {
        messages = new File(getDataFolder(), "messages.yml");
        if (!messages.exists()) {
            messages.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messages);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        createMessagesConfig();
        createRegistrationsBase();
        messagesLoader();

        login_msg = new Timer(this);
        login_msg.runTaskTimer(this, 0, 30);
        Bukkit.getPluginManager().registerEvents(new Login(this), this);
        getCommand("l").setExecutor(new Login(this));
        getCommand("reg").setExecutor(new NewAccount(this));
        getCommand("sreg").setExecutor(new ConsoleInput(this));
        getCommand("sunreg").setExecutor(new RemoveAccount(this));
        getCommand("changepass").setExecutor(new ChangePassword(this));
        getLogger().info("Плагин запущен!");

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getLogger().info("Конфиг не найден, создаю новый файл!");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    public void onDisable() {
        getLogger().info("Плагин выключен!");
    }

    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messages);
        messagesLoader();
    }

    public void reloadRegistrations() {
        registrationsBase = YamlConfiguration.loadConfiguration(registrations);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if((sender instanceof ConsoleCommandSender) && (cmd.getName().equalsIgnoreCase("sreload"))){
            reloadConfig();
            reloadMessages();
            reloadRegistrations();
            sender.sendMessage("Config successfully reloaded!");
        }
        return true;
    }

    public void saveRegistersBase() {
        try {
            this.getRegistrationsBase().save(registrations);
        } catch (IOException var2) {
            System.out.println("Ошибка! Проверьте плагин!");
        }

    }

}

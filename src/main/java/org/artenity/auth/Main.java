package org.artenity.auth;

import org.artenity.auth.command.CommandHandler;
import org.artenity.auth.database.DatabaseManager;
import org.artenity.auth.listener.JoinQuitListener;
import org.artenity.auth.session.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private DatabaseManager dbManager;
    private SessionManager sessionManager;
    private JoinQuitListener joinQuitListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        dbManager = new DatabaseManager();
        dbManager.init();

        CommandHandler commandHandler = new CommandHandler();
        getCommand("login").setExecutor(commandHandler);
        getCommand("register").setExecutor(commandHandler);
        getCommand("captcha").setExecutor(commandHandler);

        sessionManager = new SessionManager();
        joinQuitListener = new JoinQuitListener();
        Bukkit.getPluginManager().registerEvents(joinQuitListener, this);

        getLogger().info("AuthPlugin enabled.");
    }

    @Override
    public void onDisable() {
        dbManager.close();
    }

    public static Main getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public JoinQuitListener getJoinQuitListener() {
        return joinQuitListener;
    }
}

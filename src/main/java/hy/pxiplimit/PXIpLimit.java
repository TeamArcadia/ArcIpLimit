package hy.pxiplimit;

import hy.pxiplimit.command.IpLimitCmd;
import hy.pxiplimit.command.tabcomplete.IpLimitCmdTab;
import hy.pxiplimit.listener.PlayerLoginListener;
import hy.pxiplimit.message.MessageConfig;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PXIpLimit extends JavaPlugin {

    private static  PXIpLimit instance;

    @Override
    public void onEnable() {
        instance = this;

        /*--------------- CONFIG ---------------*/
        saveDefaultConfig();
        MessageConfig.setup();

        /* --------------- COMMAND ---------------*/
        PluginCommand mainCommand = getCommand("il");
        Objects.requireNonNull(mainCommand).setExecutor(new IpLimitCmd(this));
        mainCommand.setTabCompleter(new IpLimitCmdTab());

        /* --------------- LISTENER ---------------*/
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    public static PXIpLimit getInstance() {
        return instance;
    }
}

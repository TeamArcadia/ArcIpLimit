package hy.pxiplimit.command;

import hy.pxiplimit.PXIpLimit;
import hy.pxiplimit.message.Message;
import hy.pxiplimit.message.MessageKey;
import hy.pxiplimit.util.ColorCode;
import hy.pxiplimit.valid.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IpLimitCmd implements CommandExecutor {

    private final JavaPlugin plugin;

    public IpLimitCmd(PXIpLimit plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Message msgData = Message.getInstance();

        if (!(sender instanceof Player)) {
            sender.sendMessage(msgData.getMessage(MessageKey.PLAYER_ONLY));
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
            return false;
        }

        FileConfiguration config = PXIpLimit.getInstance().getConfig();

        switch (args[0]) {
            case "리로드", "reload" -> {
                if (!PermissionValidator.hasPermission(player, "reload")) return false;
                plugin.reloadConfig();
                player.sendMessage(msgData.getMessage(MessageKey.RELOAD_CONFIG));
            }

            case "아이피제한", "iplimit" -> {
                if (!PermissionValidator.hasPermission(player, "iplimit")) return false;

                if (args.length < 2) {
                    player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                    return false;
                }

                ConfigurationSection ipLimitSec = config.getConfigurationSection("ipLimit");
                if (args[1].equalsIgnoreCase("설정") || args[1].equalsIgnoreCase("set")) {
                    if (args.length < 3) {
                        player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                        return false;
                    }
                    try {
                        int maxPlayersPerIp = Integer.parseInt(args[2]);
                        ipLimitSec.set("maxPlayersPerIp", maxPlayersPerIp);
                        plugin.saveConfig();
                        player.sendMessage(msgData.getMessage(MessageKey.SET_IP_LIMIT_SUCCESSFUL));
                    } catch (NumberFormatException e) {
                        player.sendMessage(msgData.getMessage(MessageKey.NEED_INT_VALUE));
                    }
                } else if (args[1].equalsIgnoreCase("켜기") || args[1].equalsIgnoreCase("on")) {
                    ipLimitSec.set("enabled", true);
                    plugin.saveConfig();
                    player.sendMessage(msgData.getMessage(MessageKey.IP_LIMIT_ON));

                } else if (args[1].equalsIgnoreCase("끄기") || args[1].equalsIgnoreCase("off")) {
                    ipLimitSec.set("enabled", false);
                    plugin.saveConfig();
                    player.sendMessage(msgData.getMessage(MessageKey.IP_LIMIT_OFF));
                }
            }
            case "플레이어정보", "playerinfo" -> {
                if (!PermissionValidator.hasPermission(player, "playerinfo")) return false;

                String playerName = args[1];
                Player checkedPlayer = Bukkit.getPlayerExact(playerName);

                if (checkedPlayer == null) {
                    player.sendMessage(msgData.getMessage(MessageKey.CANT_FIND_PLAYER));
                    return false;
                }

                String playerIp = checkedPlayer.getAddress().getAddress().getHostAddress();
                List<String> sameIpPlayers = new ArrayList<>();

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.getAddress().getAddress().getHostAddress().equals(playerIp)
                            && !onlinePlayer.getName().equals(playerName)) { // 본인 제외
                        sameIpPlayers.add(onlinePlayer.getName());
                    }
                }
                String sameIpPlayerNumber = String.valueOf(sameIpPlayers.size());

                String sendChat =  ColorCode.colorCodes(config.getString("player_info.message")
                                .replace("{playerName}", playerName)
                                .replace("{playerIp}", playerIp)
                                .replace("{sameIpPlayerNumber}", sameIpPlayerNumber)
                                .replace("{sameIpPlayer}", String.join(", ", sameIpPlayers)));
                player.sendMessage(ColorCode.colorCodes(sendChat));
            }
            default -> {
                player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                return false;
            }
        }
        return false;
    }
}



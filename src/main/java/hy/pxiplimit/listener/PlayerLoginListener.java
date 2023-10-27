package hy.pxiplimit.listener;

import hy.pxiplimit.PXIpLimit;
import hy.pxiplimit.message.Message;
import hy.pxiplimit.message.MessageKey;
import hy.pxiplimit.valid.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerLoginListener implements Listener {
    private HashMap<String, Integer> playerIpMap = new HashMap<>();
    Message msgData = Message.getInstance();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        boolean isIpLimitEnabled = PXIpLimit.getInstance().getConfig().getBoolean("ipLimit.enabled");
        if (!isIpLimitEnabled) {
            return;
        }
        Player player = event.getPlayer();
        if (PermissionValidator.hasPermission(player,"bypass")) return;

        String playerIp = event.getAddress().getHostAddress();
        int maxPlayersPerIp = PXIpLimit.getInstance().getConfig().getInt("ipLimit.maxPlayersPerIp");
        int playerCount = playerIpMap.getOrDefault(playerIp, 0);

        if (playerCount >= maxPlayersPerIp) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, msgData.getMessage(MessageKey.KICK_MESSAGE));
        } else {
            playerIpMap.put(playerIp, playerCount + 1);
        }
    }
    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PermissionValidator.hasPermission(player, "bypass")) return;

        String playerIp = player.getAddress().getAddress().getHostAddress();

        int playerCount = playerIpMap.getOrDefault(playerIp, 0);
        if (playerCount > 0) {
            playerIpMap.put(playerIp, playerCount - 1);
        }
    }


    public HashMap<String, Integer> getPlayerIpMap() {
        return playerIpMap;
    }
}

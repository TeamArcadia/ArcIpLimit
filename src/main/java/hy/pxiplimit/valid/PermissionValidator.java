package hy.pxiplimit.valid;

import hy.pxiplimit.message.Message;
import hy.pxiplimit.message.MessageKey;
import org.bukkit.entity.Player;

public class PermissionValidator {

    public static Boolean hasPermission(Player player, String permission) {
        Message msgData = Message.getInstance();
         if (player.hasPermission("arc.iplimit." + permission)) {
            return true;
         } else {
             player.sendMessage(msgData.getMessage(MessageKey.NO_PERMISSION));
             return false;
         }
    }
}
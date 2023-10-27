package hy.pxiplimit.command.tabcomplete;

import hy.pxiplimit.valid.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IpLimitCmdTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (label.equalsIgnoreCase("il")) {
                if (PermissionValidator.hasPermission((Player) sender, "reload")) completions.add("reload");
                if (PermissionValidator.hasPermission((Player) sender, "iplimit")) completions.add("iplimit");
                if (PermissionValidator.hasPermission((Player) sender, "playerinfo")) completions.add("playerinfo");
            } else if (label.equalsIgnoreCase("아이피제한")) {
                if (PermissionValidator.hasPermission((Player) sender, "reload")) completions.add("리로드");
                if (PermissionValidator.hasPermission((Player) sender, "iplimit")) completions.add("아이피제한");
                if (PermissionValidator.hasPermission((Player) sender, "playerinfo")) completions.add("플레이어정보");
            }
        } else if (args.length == 2) {
            if ("iplimit".equalsIgnoreCase(args[0])) {
                completions.add("set");
                completions.add("on");
                completions.add("off");
            } else if ("아이피제한".equalsIgnoreCase(args[0])) {
                completions.add("설정");
                completions.add("켜기");
                completions.add("끄기");
            } else if ("playerinfo".equalsIgnoreCase(args[0]) || "플레이어정보".equalsIgnoreCase(args[0])) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (int i = 0; i < players.length; i++) {
                    playerNames.add(players[i].getName());
                }
                return playerNames;

            }
        } else if (args.length == 3) {
            if ("iplimit".equalsIgnoreCase(args[0]) && "set".equalsIgnoreCase(args[1])) {
                completions.add("<limits>");
            } else if ("아이피제한".equalsIgnoreCase(args[0]) && "설정".equalsIgnoreCase(args[1])) {
                completions.add("<지정가>");
            }
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}

package hy.pxiplimit.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageKey {
    /* --------------- NORMAL ---------------*/
    PREFIX("normal.prefix"),
    RELOAD_CONFIG("normal.reload_config"),

    /* --------------- ERROR ---------------*/
    PLAYER_ONLY("error.player_only"),
    NO_PERMISSION("error.no_permission"),
    WRONG_COMMAND("error.wrong_command"),

    /* --------------- MAIN ---------------*/
    KICK_MESSAGE("main.kick_message"),
    SET_IP_LIMIT_SUCCESSFUL("main.set_ip_limit_successful"),
    NEED_INT_VALUE("main.need_int_value"),
    IP_LIMIT_ON("main.ip_limit_on"),
    IP_LIMIT_OFF("main.ip_limit_off");



    private final String key;

}
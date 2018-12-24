package com.wossha.social.wsCommands;

public enum WsDestinations {
	SEND_TO_USER_DEST("/queue/reply");

    private final String valor;

    WsDestinations(String valor){
        this.valor = valor;
    }

    public String getValue() {
        return valor;
    }
}

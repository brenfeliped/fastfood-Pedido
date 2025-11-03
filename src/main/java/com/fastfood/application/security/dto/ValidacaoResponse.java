package com.fastfood.application.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidacaoResponse(@JsonProperty("message")
                                String message,

                                @JsonProperty("claims")

                                Claims claims) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record Claims(
            @JsonProperty("username") String username,
            @JsonProperty("token_use") String tokenUse,
            @JsonProperty("iat") Long iat,
            @JsonProperty("exp") Long exp
    ) {}

    public boolean valido() {

        if(this.claims.tokenUse != null && this.claims.username != null){
            return true;
        }
        return false;
    }
}

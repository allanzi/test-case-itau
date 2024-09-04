package com.itau.test.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BusinessConstant {

    public static final String DEFAULT_CONNECTION_TIMEOUT = "3000";
    public static final String DEFAULT_SOCKET_TIMEOUT = "5000";
    public static final String QUOTE = "/quote";

}

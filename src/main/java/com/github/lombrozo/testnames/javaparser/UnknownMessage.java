package com.github.lombrozo.testnames.javaparser;

import java.util.Optional;

final class UnknownMessage {

    private final String msg;

    UnknownMessage() {
        this("Unknown message. The message will be known only in runtime");
    }

    private UnknownMessage(final String message) {
        this.msg = message;
    }

    Optional<String> message() {
        return Optional.of(this.msg);
    }

}

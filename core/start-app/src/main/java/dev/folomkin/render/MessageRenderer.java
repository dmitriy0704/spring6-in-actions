package dev.folomkin.render;

import dev.folomkin.provider.MessageProvider;

public interface MessageRenderer { // -> Воспроизведение сообщений
    void render();
    void setMessageProvider(MessageProvider provider);
    MessageProvider getMessageProvider();
}

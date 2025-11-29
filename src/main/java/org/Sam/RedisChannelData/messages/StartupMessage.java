package org.Sam.RedisChannelData.messages;

import org.Sam.ServerTypes;

public record StartupMessage(String serverID, String sessionIID, ServerTypes serverType, int serverPort) {
}

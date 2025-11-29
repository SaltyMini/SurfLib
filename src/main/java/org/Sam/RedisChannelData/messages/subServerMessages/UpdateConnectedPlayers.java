package org.Sam.RedisChannelData.messages.subServerMessages;

import java.util.List;
import java.util.UUID;

public record UpdateConnectedPlayers(String serverID, List<UUID> currentPlayers) {
}

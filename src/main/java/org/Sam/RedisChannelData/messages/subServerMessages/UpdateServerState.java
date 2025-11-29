package org.Sam.RedisChannelData.messages.subServerMessages;

import org.Sam.ServerStates;

import java.util.List;
import java.util.UUID;

public record UpdateServerState(String serverID, ServerStates serverStates, List<UUID> currentPlayers) {
}

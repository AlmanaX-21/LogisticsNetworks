package me.almana.logisticsnetworks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.almana.logisticsnetworks.Logisticsnetworks;
import me.almana.logisticsnetworks.data.NetworkRegistry;
import me.almana.logisticsnetworks.entity.LogisticsNodeEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.almana.logisticsnetworks.data.LogisticsNetwork;

public class LogisticsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> lnCommand = Commands.literal("logisticsnetworks")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("removeNodes")
                        .executes(context -> removeNodes(context.getSource())))
                .then(Commands.literal("cullNetworks")
                        .executes(context -> cullNetworks(context.getSource())));

        LiteralArgumentBuilder<CommandSourceStack> lnAlias = Commands.literal("ln")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("removeNodes")
                        .executes(context -> removeNodes(context.getSource())))
                .then(Commands.literal("cullNetworks")
                        .executes(context -> cullNetworks(context.getSource())));

        dispatcher.register(lnCommand);
        dispatcher.register(lnAlias);
    }

    private static int removeNodes(CommandSourceStack source) {
        ServerLevel level = source.getLevel();
        List<LogisticsNodeEntity> nodes = level.getEntitiesOfClass(LogisticsNodeEntity.class,
                AABB.ofSize(source.getPosition(), 60000000, 60000000, 60000000));

        int removedCount = 0;
        for (LogisticsNodeEntity node : nodes) {
            if (node.getNetworkId() != null) {
                NetworkRegistry.get(level).removeNodeFromNetwork(node.getNetworkId(), node.getUUID());
            }

            node.dropFilters();
            node.dropUpgrades();
            node.discard(); // Safely removes it without triggering drops again via standard tick()
            removedCount++;
        }

        final int count = removedCount;
        source.sendSuccess(
                () -> Component.literal("Successfully removed " + count + " logistics nodes in this dimension."),
                true);
        return count;
    }

    private static int cullNetworks(CommandSourceStack source) {
        ServerLevel level = source.getLevel();
        NetworkRegistry registry = NetworkRegistry.get(level);

        int culledNodes = 0;
        int culledNetworks = 0;

        for (LogisticsNetwork network : registry.getAllNetworks().values()) {
            Set<UUID> nodesToRemove = new HashSet<>();
            for (UUID nodeId : network.getNodeUuids()) {
                if (level.getEntity(nodeId) == null) {
                    nodesToRemove.add(nodeId);
                }
            }

            for (UUID nodeId : nodesToRemove) {
                // removeNodeFromNetwork actually deletes the network if the node count reaches
                // 0 internally in NetworkRegistry
                registry.removeNodeFromNetwork(network.getId(), nodeId);
                culledNodes++;
            }

            if (network.getNodeUuids().isEmpty()) {
                // If it was already empty before we even tried to remove nodes, or became empty
                // registry might have already deleted it, but we can double check:
                if (registry.getNetwork(network.getId()) != null) {
                    registry.deleteNetwork(network.getId());
                }
                culledNetworks++;
            }
        }

        final int finalCulledNodes = culledNodes;
        final int finalCulledNetworks = culledNetworks;
        source.sendSuccess(
                () -> Component.literal("Culled " + finalCulledNodes + " missing nodes and " + finalCulledNetworks
                        + " empty networks."),
                true);

        return finalCulledNodes + finalCulledNetworks;
    }
}

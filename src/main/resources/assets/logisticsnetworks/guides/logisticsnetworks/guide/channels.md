---
navigation:
  title: Channels
  parent: index.md
  position: 4
---

# Channels

Each node has 9 channels, indexed 0 to 8.

## How Transfers Work

A transfer path requires:

1. A source node channel set to Export mode.
2. A target node channel with the same channel index.
3. The target channel set to Import mode.
4. The same resource type on both channels.

Channel index and resource type must match for a transfer to occur.

## Resource Types

1. Item
2. Fluid
3. Energy
4. Chemical (requires Mekanism and Mekanism Chemical Upgrade on the node)
5. Source (requires Ars Nouveau and Ars Source Upgrade on the node)

## Redstone Modes

Redstone modes control when a channel runs:

1. Always On: runs without needing redstone power.
2. Always Off: never runs.
3. High Signal: runs only when redstone signal is greater than 0.
4. Low Signal: runs only when redstone signal is 0.

## Distribution Modes

Distribution modes apply to Export channels and control the order targets are tried:

1. Priority: targets with higher priority value are tried first.
2. Nearest First: closest target node is tried first.
3. Farthest First: farthest target node is tried first.
4. Round Robin: targets are rotated after each successful transfer.
5. Recipe Robin: a specialized mode for item channels that reads per-entry amounts from the export filter.

### Recipe Robin

Recipe Robin reads the amount set on each filter entry and transfers exactly that amount of each item to one target at a time.

For example, if the export filter has Iron Ingot with amount 3 and Gold Ingot with amount 1, the channel moves exactly 3 Iron Ingots and 1 Gold Ingot to the first target before rotating.

This is useful for crafting setups where exact input ratios matter. Set per-entry amounts in the filter by hovering a slot and scrolling.

If no per-entry amounts are configured, Recipe Robin falls back to normal Round Robin behavior.

## Filter Mode

Each channel has a filter mode:

1. Any: at least one whitelist filter must match.
2. All: every whitelist filter must match.

Blacklist filter matches always block the transfer regardless of mode.

Filters are checked on Item, Fluid, and Chemical channels. Energy and Source channels do not use filter slots.

---
item_ids: [logisticsnetworks:logistics_node]
navigation:
  title: Nodes
  parent: index.md
  icon: logisticsnetworks:logistics_node
  position: 2
---

# Nodes

The Logistics Node is the core block attachment for the mod. It is an entity that attaches to a block face and handles all resource transfers.

<RecipeFor id="logisticsnetworks:logistics_node" />

## Placement Rules

1. Cannot be placed on air.
2. Cannot be placed on blacklisted blocks.
3. The block must expose at least one storage capability (items, fluids, energy, etc).
4. Only one node can be attached to one block.

## Node Structure

Each node has:

1. 9 channels (indexed 0 to 8)
2. 9 filter slots per channel
3. 4 shared upgrade slots

Channel 0 starts enabled on a fresh node. The rest start disabled.

## Removing Nodes

When the attached block is broken, the node is removed. Filters and upgrades are dropped as items. The node item is dropped if `dropNodeItem` is true in config.

You can also remove a node with the wrench by holding Shift and right clicking the node. This drops the node item, all filters, and all upgrades.

## Commands

1. `/logistics removeNodes` removes all nodes in the current dimension. Requires operator permissions.
2. `/logistics cullNetwork <name>` removes all nodes belonging to a specific network and deletes the network. Only the network owner or operators can use this.

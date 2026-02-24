---
item_ids: [logisticsnetworks:logistics_node]
navigation:
  title: Nodes
  parent: index.md
  icon: logisticsnetworks:logistics_node
  position: 2
---

# Nodes

The Logistics Node is the core block attachment for the mod.

Placement rules from code:

1. You cannot place it on air.
2. You cannot place it on blacklisted blocks.
3. The block must expose at least one storage capability.
4. Only one node can be attached to one block.

When you break the attached block, the node is removed.
Its filters and upgrades are dropped.
The node item is dropped if `dropNodeItem` is true in config.

The node entity has:

1. 9 channels
2. 9 filter slots per channel
3. 4 shared upgrade slots

Channel 0 starts enabled on a fresh node. The rest start disabled.


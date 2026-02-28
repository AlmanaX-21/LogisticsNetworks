---
item_ids: [logisticsnetworks:amount_filter]
navigation:
  title: Deprecated Amount Filter
  parent: deprecated_filters.md
  icon: logisticsnetworks:amount_filter
  position: 6
---

# Amount Filter (Deprecated)

> [!WARNING]
> This filter is deprecated. Amount filtering is now built into the base filters (Small, Medium, Big). Use scroll on any slot to set amounts.

The Amount Filter sets a stock threshold for transfers.

## Behavior

1. On the Export side, keeps stock in the source. Only moves amounts above the threshold.
2. On the Import side, caps stock in the destination. Stops filling at the threshold.

When multiple Amount filters exist on a channel:

1. Export side uses the highest threshold.
2. Import side uses the lowest threshold.

Works with Items, Fluids, and Chemicals.

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
> The isolated Amount Filter is deprecated. Amount filtering is now a built-in feature of base filters (Small, Medium, Big).

Amount Filter sets a threshold value.

How threshold is used:

1. On Export side it keeps stock in source.
2. On Import side it caps stock in destination.

For items:

1. Export allows moving only amount above threshold.
2. Import allows moving only amount below threshold.

For fluids:

1. Export keeps fluid reserve above threshold.
2. Import stops fill at threshold.

When multiple Amount filters exist on a channel:

1. Export side uses the highest threshold.
2. Import side uses the lowest threshold.

Target type can be Items, Fluids, or Chemicals.
Current transfer code applies threshold logic to Items and Fluids.


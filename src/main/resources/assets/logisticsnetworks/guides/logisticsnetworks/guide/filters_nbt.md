---
item_ids: [logisticsnetworks:nbt_filter]
navigation:
  title: Deprecated NBT Filter
  parent: deprecated_filters.md
  icon: logisticsnetworks:nbt_filter
  position: 5
---

# NBT Filter (Deprecated)

> [!WARNING]
> The isolated NBT Filter is deprecated. NBT filtering is now a built-in feature of base filters (Small, Medium, Big).

NBT Filter stores one selected path and one exact value.

Setup flow:

1. Open the NBT filtering mode for a slot (`Ctrl + Right Click`).
2. Put an item or fluid container in the extractor slot.
3. Pick a path from the detected list.
4. The value at that path is stored on the filter for that slot.

Match is exact equality on the selected value.

Target types in UI:

1. Items
2. Fluids
3. Chemicals

Current code supports matching for Items and Fluids.
There is no chemical NBT matching implementation right now.


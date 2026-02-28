---
item_ids: [logisticsnetworks:name_filter]
navigation:
  title: Name Filter
  parent: filters.md
  icon: logisticsnetworks:name_filter
  position: 4
---

# Name Filter

The Name Filter matches resources by their display name using case-insensitive substring matching.

For example, setting the value to `stone` matches any resource whose name contains "stone" (Stone, Cobblestone, Stonecutter, etc).

## Supported Types

Works with Items, Fluids, and Chemicals. Chemical name matching uses the Mekanism display text when available, then falls back to the chemical id.

<RecipeFor id="logisticsnetworks:name_filter" />

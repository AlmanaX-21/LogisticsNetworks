---
item_ids: [logisticsnetworks:durability_filter]
navigation:
  title: Durability Filter
  parent: filters.md
  icon: logisticsnetworks:durability_filter
  position: 7
---

# Durability Filter

The Durability Filter matches damageable items based on their remaining durability.

Remaining durability is calculated as: max damage minus current damage.

## Settings

1. **Operator**: less than or equal, equal, greater than or equal.
2. **Value**: 0 to 3000.

## Behavior

1. Only applies to item transfers. Does not affect fluids, chemicals, energy, or source.
2. Requires a positive match for the transfer to proceed.
3. The blacklist toggle on this filter does not invert the durability check.

<RecipeFor id="logisticsnetworks:durability_filter" />

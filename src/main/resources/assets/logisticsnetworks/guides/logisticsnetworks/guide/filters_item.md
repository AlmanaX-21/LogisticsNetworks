---
item_ids: [logisticsnetworks:small_filter, logisticsnetworks:medium_filter, logisticsnetworks:big_filter]
navigation:
  title: Item Filters
  parent: filters.md
  icon: logisticsnetworks:small_filter
  position: 1
---

# Item Filters

These are the general slot based filters:

1. Small Filter with 9 entries
2. Medium Filter with 18 entries
3. Big Filter with 27 entries

Each entry can store one target:

1. Item
2. Fluid
3. Chemical

Target type is selected in the filter GUI.

Matching details:

1. Item entries match by item id.
2. Fluid entries match by fluid with components.
3. Chemical entries match by exact chemical id.

Duplicate entries are blocked by the menu logic.

<RecipeFor id="logisticsnetworks:small_filter" />
<RecipeFor id="logisticsnetworks:medium_filter" />
<RecipeFor id="logisticsnetworks:big_filter" />


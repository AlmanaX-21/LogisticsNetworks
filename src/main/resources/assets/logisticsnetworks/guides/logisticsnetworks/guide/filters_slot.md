---
item_ids: [logisticsnetworks:slot_filter]
navigation:
  title: Slot Filter
  parent: filters.md
  icon: logisticsnetworks:slot_filter
  position: 8
---

# Slot Filter

The Slot Filter restricts which inventory slots a channel can access during item transfers.

## Input Format

Enter slot numbers using commas and ranges:

1. `0, 1, 2` for individual slots
2. `4-9` for a range
3. `0,1,4-9` for a mix of both

Slot range is 0 to 53.

## Behavior

1. In whitelist mode, only the listed slots are used.
2. In blacklist mode, the listed slots are blocked.

This applies on both sides of a transfer: export source slots for extraction and import target slots for insertion.

Only affects item channel transfers.

<RecipeFor id="logisticsnetworks:slot_filter" />

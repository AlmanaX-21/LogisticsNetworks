---
item_ids: [logisticsnetworks:name_filter]
navigation:
  title: Regex Filter
  parent: filters.md
  icon: logisticsnetworks:name_filter
  position: 4
---

# Regex Filter

The Regex Filter matches resources using **regex** (regular expression) patterns. Matching is case-insensitive and uses partial matching (the pattern can match anywhere in the text).

## Match Scope

Use the scope button to control what text the regex is matched against:

- **Name** — matches against the item/fluid/chemical display name only (default)
- **Tooltip** — matches against tooltip lines only (useful for matching enchantments, mod descriptions, etc.)
- **Both** — matches if the regex is found in either the name or any tooltip line

> For Fluids and Chemicals, tooltip matching falls back to the display name since they don't have rich tooltips.

## Regex Examples

- `stone` — matches anything containing "stone" (Stone, Cobblestone, Stonecutter, etc.)
- `^Diamond` — matches items whose name starts with "Diamond"
- `Sword$` — matches items whose name ends with "Sword"
- `Iron|Gold` — matches items containing "Iron" or "Gold"
- `^(?!Stone).*` — matches items whose name does NOT start with "Stone"

If an invalid regex is entered, the filter will show a warning and will not match anything until corrected.

## Supported Types

Works with Items, Fluids, and Chemicals. Chemical name matching uses the Mekanism display text when available, then falls back to the chemical id.

<RecipeFor id="logisticsnetworks:name_filter" />

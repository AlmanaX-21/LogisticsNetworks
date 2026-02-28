---
item_ids: [logisticsnetworks:iron_upgrade, logisticsnetworks:gold_upgrade, logisticsnetworks:diamond_upgrade, logisticsnetworks:netherite_upgrade, logisticsnetworks:dimensional_upgrade, logisticsnetworks:mekanism_chemical_upgrade, logisticsnetworks:ars_source_upgrade]
navigation:
  title: Upgrades
  parent: index.md
  position: 6
---

# Upgrades

Nodes have 4 upgrade slots shared across all channels. The highest speed tier installed on a node determines the caps for that node.

## Tier Limits

Default values from `config/logistics-network/upgrades.json`:

1. **None**: min delay 20, item 8, fluid 500, energy 2000, chemical 500, source 500
2. **Iron**: min delay 10, item 16, fluid 1000, energy 10000, chemical 1000, source 1000
3. **Gold**: min delay 5, item 32, fluid 5000, energy 50000, chemical 5000, source 5000
4. **Diamond**: min delay 1, item 64, fluid 20000, energy 250000, chemical 20000, source 20000
5. **Netherite**: min delay 1, item 10000, fluid 1000000, energy unlimited, chemical 1000000, source 1000000

These values can be changed in the config file.

## Speed Upgrades

### Iron Upgrade

<RecipeFor id="logisticsnetworks:iron_upgrade" />

### Gold Upgrade

<RecipeFor id="logisticsnetworks:gold_upgrade" />

### Diamond Upgrade

<RecipeFor id="logisticsnetworks:diamond_upgrade" />

### Netherite Upgrade

<RecipeFor id="logisticsnetworks:netherite_upgrade" />

## Special Upgrades

### Dimensional Upgrade

Allows cross-dimension transfers. Both the source and target nodes must have this upgrade installed.

<RecipeFor id="logisticsnetworks:dimensional_upgrade" />

### Mekanism Chemical Upgrade

Unlocks the Chemical channel type on the node. Only works when Mekanism is installed.

<RecipeFor id="logisticsnetworks:mekanism_chemical_upgrade" />

### Ars Source Upgrade

Unlocks the Source channel type on the node. Only works when Ars Nouveau is installed.

<RecipeFor id="logisticsnetworks:ars_source_upgrade" />

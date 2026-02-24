---
navigation:
  title: Filters
  parent: index.md
  position: 7
---

# Filters

Filters are read from both ends of a transfer.

For supported channel types, transfer checks:

1. Export channel filter slots on the source node
2. Import channel filter slots on the target node

So filters are bidirectional in practice.

The channel filter mode changes whitelist behavior:

1. Any means at least one whitelist filter can match.
2. All means all whitelist filters must match.
3. Blacklist matches always block.

Open each filter page below for exact behavior.

<SubPages icons={true} />


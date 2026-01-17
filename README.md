# Campus Map Core Logic

**Context:** Data Structures & Algorithms (CSE 373) Capstone Project

**Goal:** Backend Algorithm Implementation

**Tech Stack:** Java, JUnit Testing, Git

## Project Overview
This repository contains the backend logic I implemented for **Husky Maps**, a campus routing application. As part of the University of Washington's data structures curriculum, I was responsible for engineering the core algorithms that power the application's search and navigation features within a provided server and website framework.

## Implemented Components

### [Graph Algorithms & Routing](src/main/java/graphs/)
* **Routing Engine:** Implemented **Dijkstra’s Algorithm** to calculate optimal routes across the campus map.
* **Topological Sort:** Implemented `ToposortDAGSolver` using **Depth-First Search (DFS)** post-order traversal to handle graph dependencies.

### [Seam Carving & Image Processing](src/main/java/seamfinding/)
* **Content-Aware Resizing:** Implemented `GenerativeSeamFinder` to remove low-energy pixels from images while preserving important content.
* **Implicit Graph Strategy:** Utilized a dynamic graph approach where vertices and edges are generated on-the-fly (when `neighbors` is called). This allowed the shortest-path algorithm to traverse image data with minimal memory overhead.

### [Optimized Priority Queues](src/main/java/minpq/)
* **Custom Heap Implementation:** Engineered `OptimizedHeapMinPQ` using a **HashMap** to achieve $O(\log N)$ time complexity for priority updates.
* **Performance Analysis:** Compared performance against baseline implementations (`UnsortedArrayMinPQ`, `HeapMinPQ`) to validate efficiency gains.

### [Autocomplete System](src/main/java/autocomplete/)
* **Prefix Matching:** Built `BinarySearchAutocomplete` to enable rapid location searching using binary search logic.
* **Optimization:** Refactored the `allMatches()` algorithm to break loops early once prefix mismatches were detected, significantly reducing runtime for large dictionaries.

### [Data Structures (Deques)](src/main/java/deques/)
* **Buffer Management:** Implemented `LinkedDeque` and `ArrayDeque` from scratch.
* Debugged and resolved complex circular indexing logical errors to ensure memory safety.

## Testing & Validation
* Wrote and executed **JUnit tests** to verify edge cases for Autocomplete and Priority Queue logic.
* Conducted regression testing to resolve pipeline failures and indexing bugs during the development cycle.

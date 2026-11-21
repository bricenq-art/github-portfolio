import java.util.*;

public class SimpleCache {

    private static class CacheSet {
        // Front = least recently used, back = most recently used.
        LinkedList<Integer> blocks = new LinkedList<>();
    }

    private static class SimulationResult {
        int misses;
        int accesses;
        List<CacheSet> sets;

        SimulationResult(int misses, int accesses, List<CacheSet> sets) {
            this.misses = misses;
            this.accesses = accesses;
            this.sets = sets;
        }

        double missRate() {
            return accesses == 0 ? 0.0 : (100.0 * misses / accesses);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Simple Cache Simulator");
        System.out.print("Enter number of cache blocks: ");
        int numBlocks = input.nextInt();

        if (numBlocks <= 0) {
            System.out.println("Number of cache blocks must be positive.");
            return;
        }

        System.out.println("\nAssociativity options:");
        System.out.println("1 - Direct mapped");
        System.out.println("2 - 2-way set associative (LRU)");
        System.out.println("3 - 4-way set associative (LRU)");
        System.out.println("4 - Fully associative (LRU)");
        System.out.print("Choose an option: ");
        int option = input.nextInt();

        int ways;
        String name;

        switch (option) {
            case 1:
                ways = 1;
                name = "Direct-mapped";
                break;
            case 2:
                ways = 2;
                name = "2-way set associative (LRU)";
                break;
            case 3:
                ways = 4;
                name = "4-way set associative (LRU)";
                break;
            case 4:
                ways = numBlocks;
                name = "Fully associative (LRU)";
                break;
            default:
                System.out.println("Invalid associativity option.");
                return;
        }

        if (numBlocks % ways != 0) {
            System.out.println("Error: the number of cache blocks must be divisible by the number of ways.");
            return;
        }

        System.out.print("Enter number of block address references: ");
        int count = input.nextInt();

        if (count < 0) {
            System.out.println("Reference count cannot be negative.");
            return;
        }

        int[] references = new int[count];
        System.out.println("Enter the block addresses separated by spaces:");
        for (int i = 0; i < count; i++) {
            references[i] = input.nextInt();
            if (references[i] < 0) {
                System.out.println("Block addresses must be nonnegative.");
                return;
            }
        }

        System.out.println("\nReplacement policy: " + (ways == 1 ? "Not applicable" : "LRU"));
        SimulationResult result = simulate(numBlocks, ways, references, true);

        System.out.println("\n=== Final Results ===");
        System.out.println("Cache organization: " + name);
        System.out.println("Total references: " + result.accesses);
        System.out.println("Cache misses: " + result.misses);
        System.out.println("Cache hits: " + (result.accesses - result.misses));
        System.out.printf("Miss rate: %.2f%%%n", result.missRate());
        printFinalCache(result.sets, ways);
    }

    private static SimulationResult simulate(int numBlocks, int ways, int[] references, boolean showSteps) {
        int numSets = numBlocks / ways;
        List<CacheSet> sets = new ArrayList<>();

        for (int i = 0; i < numSets; i++) {
            sets.add(new CacheSet());
        }

        int misses = 0;

        if (showSteps) {
            System.out.println("\nAccess\tResult\tSet\tCache contents after reference");
        }

        for (int address : references) {
            // Block address maps to a set using modulo number of sets.
            int setIndex = address % numSets;
            CacheSet set = sets.get(setIndex);

            boolean hit = set.blocks.contains(address);

            if (hit) {
                // LRU update: a referenced block becomes most recently used.
                if (ways > 1) {
                    set.blocks.remove((Integer) address);
                    set.blocks.addLast(address);
                }
            } else {
                misses++;

                if (set.blocks.size() == ways) {
                    // Remove least recently used block.
                    set.blocks.removeFirst();
                }

                set.blocks.addLast(address);
            }

            if (showSteps) {
                System.out.printf("%d\t%s\t%d\t%s%n",
                        address, hit ? "Hit" : "Miss", setIndex,
                        cacheState(sets, ways));
            }
        }

        return new SimulationResult(misses, references.length, sets);
    }

    private static String cacheState(List<CacheSet> sets, int ways) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < sets.size(); i++) {
            if (i > 0) out.append(" | ");
            out.append("Set ").append(i).append(": [");

            CacheSet set = sets.get(i);
            for (int j = 0; j < ways; j++) {
                if (j > 0) out.append(", ");
                if (j < set.blocks.size()) {
                    out.append("Mem[").append(set.blocks.get(j)).append("]");
                } else {
                    out.append("-");
                }
            }
            out.append("]");
        }

        return out.toString();
    }

    private static void printFinalCache(List<CacheSet> sets, int ways) {
        System.out.println("Final cache contents (LRU -> MRU within each set):");
        for (int i = 0; i < sets.size(); i++) {
            System.out.print("Set " + i + ": ");
            CacheSet set = sets.get(i);

            for (int j = 0; j < ways; j++) {
                if (j < set.blocks.size()) {
                    System.out.print("Mem[" + set.blocks.get(j) + "]");
                } else {
                    System.out.print("-");
                }

                if (j < ways - 1) System.out.print("  ");
            }
            System.out.println();
        }
    }
}

package com.sai.waveLabs;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        int[][] times = {{2,1,1}, {2,3,1}, {3,4,1}};
        int n = 4, k = 2;
        
        Solution solution = new Solution();
        int result = solution.networkDelayTime(times, n, k);
        
        System.out.println(result); 
    }
}

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0], v = time[1], w = time[2];
            graph.computeIfAbsent(u, ArrayList::new).add(new int[] {v, w});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[] {k, 0});
        Set<Integer> processed = new HashSet<>();
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0], d = curr[1];
            if (processed.contains(u)) continue;
            processed.add(u);
            for (int[] neighbor : graph.getOrDefault(u, Collections.emptyList())) {
                int v = neighbor[0], w = neighbor[1];
                if (d + w < dist[v]) {
                    dist[v] = d + w;
                    pq.offer(new int[] {v, dist[v]});
                }
            }
        }
        int maxDist = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxDist = Math.max(maxDist, dist[i]);
        }
        
        return maxDist;
    }
}


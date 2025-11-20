import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target) return 0;

        int n = routes.length;

        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();

        for(int bus=0; bus < n; bus++){
            for(int stop : routes[bus]){
                stopToBuses.computeIfAbsent(stop, k->new ArrayList<>()).add(bus);
            }
        }

        if(!stopToBuses.containsKey(source)) return -1;

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visitedBus = new boolean[n];
        Set<Integer> visitedStop = new HashSet<>();

        for(int bus : stopToBuses.getOrDefault(source, Collections.emptyList())){
            queue.offer(bus);
            visitedBus[bus] = true;
        }

        visitedStop.add(source);

        int busesTaken = 1;
        while(!queue.isEmpty()){
            int size = queue.size();

            for(int i=0; i< size; i++){
                int bus = queue.poll();

                for(int stop : routes[bus]){
                    if(stop == target){
                        return busesTaken;
                    }

                    if(visitedStop.add(stop)){
                        for(int nextBus : stopToBuses.getOrDefault(stop, Collections.emptyList())){
                            if(!visitedBus[nextBus]){
                                visitedBus[nextBus] = true;
                                queue.offer(nextBus);
                            }
                        }
                    }
                }
            }

            busesTaken++;
        }

        return -1;

    }

}
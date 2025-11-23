// You are building a daily spam summary using call and report logs to determine how many times each caller is linked to reported spam activity. 
// Given: A list of call logs calls, where each element is a triplet [caller, receiver, timestamp], and timestamp is in ISO 8601 format: "YYYY-MM-DDTHH:MM:SS". 
// A list of spam report logs reports, where each element is a pair [receiver, timestamp], indicating that the given receiver reported a spam call at that timestamp.

import java.util.*;

public class SpamCallerCounter {

    public static Map<String, Integer> countSpamCalls(
            List<String[]> calls,
            List<String[]> reports
    ) {
        // 1. Map (receiver, timestamp) -> caller
        Map<String, String> callIndex = new HashMap<>();
        for (String[] call : calls) {
            String caller = call[0];
            String receiver = call[1];
            String timestamp = call[2];
            String key = receiver + "#" + timestamp; // composite key
            callIndex.put(key, caller);
        }

        // 2. Count spam per caller
        Map<String, Integer> spamCount = new HashMap<>();

        for (String[] report : reports) {
            String receiver = report[0];
            String timestamp = report[1];
            String key = receiver + "#" + timestamp;

            String caller = callIndex.get(key);
            if (caller == null) {
                // report without matching call (can happen?) → often ignored
                continue;
            }

            spamCount.put(caller, spamCount.getOrDefault(caller, 0) + 1);
        }

        return spamCount;
    }

    public static void main(String[] args) {
        List<String[]> calls = Arrays.asList(
            new String[]{"A", "X", "2025-11-19T10:00:00"},
            new String[]{"B", "Y", "2025-11-19T10:05:00"},
            new String[]{"A", "Y", "2025-11-19T10:10:00"}
        );

        List<String[]> reports = Arrays.asList(
            new String[]{"X", "2025-11-19T10:00:00"},
            new String[]{"Y", "2025-11-19T10:10:00"}
        );

        Map<String, Integer> res = countSpamCalls(calls, reports);
        System.out.println(res); // {A=2}
    }
}

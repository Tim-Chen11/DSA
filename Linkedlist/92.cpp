class Solution {
public:
    ListNode* reverseBetween(ListNode* head, int left, int right) {
        if (!head || left == right) return head;

        ListNode dummy(0);
        dummy.next = head;
        ListNode* prev = &dummy;

        // Step 1: move prev to node before left
        for (int i = 1; i < left; i++) {
            prev = prev->next;
        }

        // Step 2: reverse sublist
        ListNode* curr = prev->next;
        ListNode* next = nullptr;
        ListNode* prevSub = nullptr;

        for (int i = 0; i < right - left + 1; i++) {
            next = curr->next;
            curr->next = prevSub;
            prevSub = curr;
            curr = next;
        }

        // Step 3: reconnect
        prev->next->next = curr;
        prev->next = prevSub;

        return dummy.next;
    }
};

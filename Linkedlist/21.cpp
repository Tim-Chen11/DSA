/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* mergeTwoLists(ListNode* list1, ListNode* list2) {
        ListNode* a = list1;
        ListNode* b = list2;

        ListNode dummy(0);
        ListNode* ans = &dummy;

        while(a && b){
            if(a->val<=b->val){
                ans->next = a;
                a = a->next;
            }else{
                ans->next = b;
                b = b->next;
            }
            ans = ans->next;
        }

        ans->next = a ? a : b;

        return dummy.next;
    }
};
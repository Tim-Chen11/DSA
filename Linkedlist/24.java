class Solution {
    public ListNode swapPairs(ListNode head) {
          ListNode dumyhead = new ListNode(-1);
          dumyhead.next = head;

          ListNode cur = dumyhead;
          ListNode temp;
          ListNode firstnode;
          ListNode secondnode;
          while (cur.next != null && cur.next.next != null) {
              temp = cur.next.next.next;
              firstnode = cur.next;
              secondnode = cur.next.next;
              cur.next = secondnode;
              secondnode.next = firstnode;
              firstnode.next = temp;
              cur = firstnode;
          }
          return dumyhead.next;  
      }
  }
class solution{
    public ListNode removeElements(ListNode head, int val) {
       

        if(head == null){
            return null;
        }

        if(head->val == val){
            return head;
        }

        ListNode next = head.next, current = head;

        while(next != null){
            if(next.val == val){
                current.next = next.next;
            }
            cur = cur.next;
            next = next.next;
        }

        return head;
    }
}
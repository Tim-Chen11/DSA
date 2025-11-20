// 给两个nested set，然后写一个function去比较是不是相等。
// 比如：setEquals([a,[b,[c],d]],[[d,b,[c]],a])=True

import java.util.*;

public class NestedSetEq {

    public static boolean setEquals(Object a, Object b) {
        return canonical(a).equals(canonical(b));
    }

    // ---- 核心函数：把对象变成一个“标准化结构” ----
    private static Object canonical(Object x) {
        // ✔ Base case：原子（不是 List） → 变成 String（可比较）
        if (!(x instanceof List<?>)) {
            return x.toString();
        }

        // ✔ Recursive case：List → 拿到它的子元素
        List<?> lst = (List<?>) x;

        // 递归 canonical 并放入一个新的数组
        List<Object> processed = new ArrayList<>();
        for (Object child : lst) {
            processed.add(canonical(child));
        }

        // ✔ Sorting：先原子后 List，内容按字符串比较
        processed.sort((o1, o2) -> {
            int t1 = (o1 instanceof List) ? 1 : 0;
            int t2 = (o2 instanceof List) ? 1 : 0;

            if (t1 != t2) return t1 - t2;
            return o1.toString().compareTo(o2.toString());
        });

        // ✔ Freeze：变成不可变 List（类似 Python 的 tuple）
        return List.copyOf(processed);
    }

    public static void main(String[] args) {
        List<Object> A = Arrays.asList("a", Arrays.asList("b", Arrays.asList("c"), "d"));
        List<Object> B = Arrays.asList(Arrays.asList("d", "b", Arrays.asList("c")), "a");

        System.out.println(setEquals(A, B));  // true
    }
}

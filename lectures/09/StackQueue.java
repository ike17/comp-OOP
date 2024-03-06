// Examples of how to create stacks and queues in Java

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

class StackQueue {
  public static void main(String[] args) {
    Deque<String> stack = new ArrayDeque<>();   // could use LinkedList here
    stack.push("Alice");
    stack.push("Bob");
    stack.push("Charlie");

    System.out.println("Removing from stack:");
    while (!stack.isEmpty()) {
      System.out.println(stack.pop());
    }

    Queue<String> queue = new ArrayDeque<>();   // could use LinkedList here
    queue.add("Alice");
    queue.add("Bob");
    queue.add("Charlie");

    System.out.println("\nRemoving from queue:");
    while (!queue.isEmpty()) {
      System.out.println(queue.remove());
    }
  }
}

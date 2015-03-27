package org.gufroan.wearwolf.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliyistomov on 27/03/15.
 */
public class Node<T> {

    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    public Node(final T data, final Node<T> parent) {
        this.data = data;
    }

    public void addChild(final Node<T> childNode) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(childNode);
    }

    public Node<T> getChild(final int index) {
        return children.get(index);
    }

    public Node<T> getParent() {
        return parent;
    }
}

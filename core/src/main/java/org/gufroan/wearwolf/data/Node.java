package org.gufroan.wearwolf.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliyistomov on 27/03/15.
 */
public class Node<T> {

    private T data;
    private Node<T> parent;
    private List<Node<T>> children = new ArrayList<>();

    public Node(final T data, final Node<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public void addChild(final Node<T> childNode) {
        children.add(childNode);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public Node<T> getChild(final int index) {
        return children.get(index);
    }

    public Node<T> getParent() {
        return parent;
    }

    public T getData() {
        return data;
    }

    public String getPathForData() {
        if (parent == null) {
            return getData().toString();
        } else {
            return getParent().getPathForData() + "/" + getData().toString();
        }
    }

    public Node<T> findByData(final String data) {
        for (Node<T> child : getChildren()) {
            if (child.toString().equals(data)){
                return child;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getData().toString();
    }
}

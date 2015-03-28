package org.gufroan.wearwolf;

import android.content.res.Resources;
import android.content.res.TypedArray;

import org.gufroan.wearwolf.data.Node;
import org.gufroan.wearwolf.data.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliyistomov on 27/03/15.
 */
public class NavigationEngine {

    private static Node<Part> content_root = new Node<>(new Part("root_element"), null);

    private static Node<Part> cursor = content_root;

    public static void populateList(TypedArray masterList, Resources res, Node<Part> parent) {

        if (parent == null)
            parent = cursor;

        int length = masterList.length();
        for (int i = 0; i < length; ++i) {
            String value = masterList.getString(i);
            if (value.startsWith("@")) {
            //if (type.contains("array")) {
                int id = masterList.getResourceId(i, 0);
                Node<Part> category = new Node<>(new Part(res.getResourceEntryName(id)), parent);
                populateList(res.obtainTypedArray(id), res, category);
            }
            else
                parent.addChild(new Node<>(new Part(value), parent));
        }
        if (parent.getParent() == null) { // if this is the root element
            masterList.recycle(); // Important!
            content_root = parent;
            cursor = parent;
        } else
            parent.getParent().addChild(parent);
    }

    public static Part navigateTo(int position) {
        cursor = cursor.getChild(position);
        return cursor.getData();
    }

    public static List<Part> getCurrentItems() {
        final List<Part> parts = new ArrayList<>();
        for (Node<Part> node : cursor.getChildren()) {
            parts.add(node.getData());
        }

        return parts;
    }

    public static Part goBack() {
        Part result = null;
        if (cursor.getParent() != null) {
            cursor = cursor.getParent();
            result = cursor.getData();

        }

        return result;
    }

    public static List<String> getValues() {
        return getValues(CONTENT);
    }

    public static List<String> getValues(Node<Part> current) {
        final List<String> list = new ArrayList<>();
        for (final Node<Part> node : current.getChildren()) {
            list.addAll(getValues(node));
        }

        return list;
    }
}

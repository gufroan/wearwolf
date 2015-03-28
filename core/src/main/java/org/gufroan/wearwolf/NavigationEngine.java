package org.gufroan.wearwolf;

import org.gufroan.wearwolf.data.Node;
import org.gufroan.wearwolf.data.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliyistomov on 27/03/15.
 */
public class NavigationEngine {

    private static final Node<Part> CONTENT = new Node<>(new Part("root_element"), null);

    private static Node<Part> cursor = CONTENT;

    static {
        Node<Part> currentParent = CONTENT;
        CONTENT.addChild(new Node<>(new Part("0"), currentParent));
        CONTENT.addChild(new Node<>(new Part("1"), currentParent));
        CONTENT.addChild(new Node<>(new Part("2"), currentParent));
        currentParent = currentParent.getChild(0); // /0
        currentParent.addChild(new Node<>(new Part("00"), currentParent));
        currentParent.addChild(new Node<>(new Part("01"), currentParent));
        currentParent.addChild(new Node<>(new Part("02"), currentParent));
        currentParent = currentParent.getParent().getChild(1); // /1
        currentParent.addChild(new Node<>(new Part("10"), currentParent));
        currentParent.addChild(new Node<>(new Part("11"), currentParent));
        currentParent.addChild(new Node<>(new Part("12"), currentParent));
        currentParent = currentParent.getParent().getChild(2); // /2
        currentParent.addChild(new Node<>(new Part("20"), currentParent));
        currentParent.addChild(new Node<>(new Part("21"), currentParent));
        currentParent.addChild(new Node<>(new Part("22"), currentParent));
        currentParent = currentParent.getParent().getChild(0).getChild(0); // /00
        currentParent.addChild(new Node<>(new Part("000"), currentParent));
        currentParent.addChild(new Node<>(new Part("001"), currentParent));
        currentParent.addChild(new Node<>(new Part("002"), currentParent));
        currentParent.addChild(new Node<>(new Part("003"), currentParent));
        currentParent = currentParent.getParent().getChild(1); // /01
        currentParent.addChild(new Node<>(new Part("010"), currentParent));
        currentParent.addChild(new Node<>(new Part("011"), currentParent));
        currentParent.addChild(new Node<>(new Part("012"), currentParent));
        currentParent.addChild(new Node<>(new Part("013"), currentParent));
        currentParent = currentParent.getParent().getChild(1); // /02
        currentParent.addChild(new Node<>(new Part("020"), currentParent));
        currentParent.addChild(new Node<>(new Part("021"), currentParent));
        currentParent.addChild(new Node<>(new Part("022"), currentParent));
        currentParent.addChild(new Node<>(new Part("023"), currentParent));
        currentParent = currentParent.getParent().getParent().getChild(1).getChild(0); // /10
        currentParent.addChild(new Node<>(new Part("100"), currentParent));
        currentParent.addChild(new Node<>(new Part("101"), currentParent));
        currentParent.addChild(new Node<>(new Part("102"), currentParent));
        currentParent.addChild(new Node<>(new Part("103"), currentParent));
        currentParent = currentParent.getParent().getChild(1); // /11
        currentParent.addChild(new Node<>(new Part("110"), currentParent));
        currentParent.addChild(new Node<>(new Part("111"), currentParent));
        currentParent.addChild(new Node<>(new Part("112"), currentParent));
        currentParent.addChild(new Node<>(new Part("113"), currentParent));
        currentParent = currentParent.getParent().getChild(1); // /12
        currentParent.addChild(new Node<>(new Part("120"), currentParent));
        currentParent.addChild(new Node<>(new Part("121"), currentParent));
        currentParent.addChild(new Node<>(new Part("122"), currentParent));
        currentParent.addChild(new Node<>(new Part("123"), currentParent));
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
            result = cursor.getParent().getData();
        }

        return result;
    }
}

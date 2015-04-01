package org.gufroan.wearwolf;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import org.gufroan.core.R;
import org.gufroan.p2p.UpdateCommand;
import org.gufroan.wearwolf.data.Node;
import org.gufroan.wearwolf.data.Part;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by vitaliyistomov on 27/03/15.
 */
public class NavigationEngine {

    private static final String CUSTOM_NODES_DESCRIPTION_STORAGE_FILE = "/custom_nodes.txt";

    private static final Node<Part> CONTENT_ROOT = new Node<>(new Part("root_element"), null);

    private static Node<Part> sNavigationCursor = CONTENT_ROOT;

    private static Stack<Integer> breadCrumbs = new Stack<>();

    public static void writeCustomElementToFileStorage(final Context ctx, final String value) {
        try {
            final Writer output = new BufferedWriter(new FileWriter(ctx.getFilesDir() + CUSTOM_NODES_DESCRIPTION_STORAGE_FILE, true));
            output.append(sNavigationCursor.getData().getStringData()).append("|").append(value);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToCurrentNode(Context context, final String value) {
        Node<Part> newNode = new Node<>(new Part(value), sNavigationCursor);
        sNavigationCursor.addChild(newNode);
        new UpdateCommand(context, newNode.getPathForData(), newNode.getData()).execute();
    }

    public static void populateList(final Context ctx) {
        final Resources resources = ctx.getResources();
        final TypedArray masterList = resources.obtainTypedArray(R.array.master);
        populateList(masterList, resources, null);
        populateCustomNodes(ctx);
    }

    private static void populateCustomNodes(final Context ctx) {
        final Scanner scanner;
        try {
            scanner = new Scanner(new File(ctx.getFilesDir() + CUSTOM_NODES_DESCRIPTION_STORAGE_FILE));

            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                if (line != null) {
                    final Node<Part> node = parseRecord(line);
                    if ((node != null) && (node.getParent() != null)) {
                        node.getParent().addChild(node);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Node<Part> parseRecord(final String line) {
        final Node<Part> result;
        final List<String> lines = Arrays.asList(line.split("|"));
        if (lines.size() == 2) {
            final String parentName = lines.get(0);
            final String value = lines.get(1);
            if (parentName != null && !parentName.isEmpty()) {
                result = new Node<>(new Part(value), getParentByName(parentName, CONTENT_ROOT));
            } else {
                result = null;
            }
        } else {
            result = null;
        }

        return result;
    }

    private static Node<Part> getParentByName(final String parentName, final Node<Part> cursor) {
        Node<Part> result = null;
        for (Node<Part> node : cursor.getChildren()) {
            if (parentName.equals(node.getData().getStringData())) {
                result = node;
                break;
            } else {
                final Node<Part> tmp = getParentByName(parentName, node);
                if (tmp != null) {
                    result = tmp;
                    break;
                }
            }
        }

        return result;
    }

    private static void populateList(TypedArray masterList, Resources res, Node<Part> currentNode) {
        if (currentNode == null) {
            currentNode = CONTENT_ROOT;
        }

        int length = masterList.length();
        for (int i = 0; i < length; ++i) {
            String value = masterList.getString(i);
            if (value.startsWith("@")) {
                //if (type.contains("array")) {
                int id = masterList.getResourceId(i, 0);
                Node<Part> category = new Node<>(new Part(res.getResourceEntryName(id).replace('_', ' ')), currentNode);
                populateList(res.obtainTypedArray(id), res, category);
            } else {
                if (value.startsWith("…")) {
                    value = (currentNode.getData().getStringData() + value).replace('…', ' ');
                }

                currentNode.addChild(new Node<>(new Part(value), currentNode));
            }
        }

        if (currentNode.getParent() == null) { // if this is the root element
            masterList.recycle(); // Important!
        } else {
            currentNode.getParent().addChild(currentNode);
        }
    }

    public static Part navigateTo(int position) {
        sNavigationCursor = sNavigationCursor.getChild(position);
        breadCrumbs.add(position);
        return sNavigationCursor.getData();
    }

    public static List<Part> getCurrentItems() {
        final List<Part> parts = new ArrayList<>();
        for (Node<Part> node : sNavigationCursor.getChildren()) {
            parts.add(node.getData());
        }

        return parts;
    }

    public static Part goBack() {
        Part result = null;
        if (sNavigationCursor.getParent() != null) {
            sNavigationCursor = sNavigationCursor.getParent();
            result = sNavigationCursor.getData();
            breadCrumbs.pop();
        }

        return result;
    }

    public static ArrayList<Integer> getBreadCrumbs() {
        Integer[] array = new Integer[breadCrumbs.size()];
        breadCrumbs.toArray(array);
        List<Integer> result = Arrays.asList(array);
        Collections.reverse(result);
        return new ArrayList<>(result);
    }

    public static List<String> getValues() {
        return getValues(CONTENT_ROOT);
    }

    public static List<String> getValues(Node<Part> current) {
        final List<String> list = new ArrayList<>();
        for (final Node<Part> node : current.getChildren()) {
            list.addAll(getValues(node));
        }

        list.add(current.getData().getStringData());

        return list;
    }

    public static void addTo(final String path, final Part part) {
        final String[] pathParts = path.split("/");
        Node<Part> currentNode = CONTENT_ROOT;
        for (int i = 3; i < pathParts.length; i++) {
            final Node<Part> newNode = currentNode.findByData(pathParts[i]);
            if (newNode != null) {
                currentNode = newNode;
                break;
            } else {
                // TODO
                return;
            }
        }
        Node<Part> newNode = new Node<>(part, currentNode);
        currentNode.addChild(newNode);
    }
}

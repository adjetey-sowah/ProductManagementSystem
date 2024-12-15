package com.juls.lab.productmanagementsystem.util;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductCategoryBinaryTree {

    private BinaryTreeNode root;

    public void insertCategory(Category category){
        root = insertRecursive(root, category);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode node, Category category){
        if(node == null){
            return new BinaryTreeNode(category);
        }
        if(category.getName().compareTo(node.getCategory().getName()) < 0){
            node.setLeft(insertRecursive(node.getLeft(), category));
        }
        else if(category.getName().compareTo(node.getCategory().getName()) > 0){
            node.setRight(insertRecursive(node.getRight(), category));
        }
        return node;
    }
    // Search for a category
    public BinaryTreeNode search(String categoryName){
        return searchRecursive(root, categoryName);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, String categoryName){
        if (node == null || node.getCategory().getName().equals(categoryName)){
            return node;
        }
        if(categoryName.compareTo(node.getCategory().getName()) < 0){
            return searchRecursive(node.getLeft(), categoryName);
        }
        return searchRecursive(node.getRight(), categoryName);
    }

    public List<Product> getProductsUnderCategory(String categoryName){
        BinaryTreeNode categoryNode = search(categoryName);
        if(categoryNode == null){
            return Collections.emptyList();
        }

        List<Product> products = new ArrayList<>();
        collectProducts(categoryNode, products);
        return products;
    }

    private void collectProducts(BinaryTreeNode node, List<Product> products){
        if (node == null){
            return;
        }

        // Add products in the current category.
        products.addAll(node.getProducts());

        // Recurse for left and right subcategories
        collectProducts(node.getLeft(), products);
        collectProducts(node.getRight(),products);

    }

    // Remove all products of a specific category
    public void removeCategory(String category) {
        if (root == null || category == null) {
            return;
        }
        root = removeRecursive(root, category);
    }

    private BinaryTreeNode removeRecursive(BinaryTreeNode node, String categoryName) {
        if (node == null) {
            return null;
        }

        // Compare category names to traverse the tree
        if (categoryName.compareTo(node.getCategory().getName()) < 0) {
            node.setLeft(removeRecursive(node.getLeft(), categoryName));
        }
        else if (categoryName.compareTo(node.getCategory().getName()) > 0) {
            node.setRight(removeRecursive(node.getRight(), categoryName));
        }
        else {
            // Category found, handle removal cases

            // Case 1: No children or only one child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 2: Two children
            // Find the smallest value in the right subtree (successor)
            BinaryTreeNode successor = findMin(node.getRight());
            // Copy successor's category to current node
            node.setCategory(successor.getCategory());
            // Remove the successor
            node.setRight(removeRecursive(node.getRight(), successor.getCategory().getName()));
        }

        return node;
    }

    private BinaryTreeNode findMin(BinaryTreeNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }


}

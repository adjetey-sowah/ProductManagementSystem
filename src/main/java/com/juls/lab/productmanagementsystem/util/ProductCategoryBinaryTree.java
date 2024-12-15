package com.juls.lab.productmanagementsystem.util;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductCategoryBinaryTree {

    private BinaryTreeNode root;

    public void insertCategory(Category category){
        root = insertRecursive(root, category);
    }

    private BinaryTreeNode insertRecursive(BinaryTreeNode node, Category category){
        if(node == null){
            return new BinaryTreeNode(category);
        }

        if(category.getCategoryName().compareTo(node.getCategory().getCategoryName()) < 0){
            node.setLeft(insertRecursive(node.getLeft(), category));
        }
        else if(category.getCategoryName().compareTo(node.getCategory().getCategoryName()) > 0){
            node.setRight(insertRecursive(node.getRight(), category));
        }
        return node;
    }

    // Search for a category
    public BinaryTreeNode search(String categoryName){
        return searchRecursive(root, categoryName);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, String categoryName){
        if (node == null || node.getCategory().getCategoryName().equals(categoryName)){
            return node;
        }
        if(categoryName.compareTo(node.getCategory().getCategoryName()) < 0){
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

}

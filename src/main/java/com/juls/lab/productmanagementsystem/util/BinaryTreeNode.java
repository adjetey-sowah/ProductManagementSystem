package com.juls.lab.productmanagementsystem.util;

import com.juls.lab.productmanagementsystem.data.model.Category;
import com.juls.lab.productmanagementsystem.data.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BinaryTreeNode {

    private Category category;
    private BinaryTreeNode left;
    private BinaryTreeNode right;
    private List<Product> products;

    public BinaryTreeNode(Category category){
        this.category = category;
        this.products = new ArrayList<>();
    }
}

package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.dto.ProductDTO;
import com.juls.lab.productmanagementsystem.exception.ProductNotFoundException;
import com.juls.lab.productmanagementsystem.exception.ResourceNotFoundException;
import com.juls.lab.productmanagementsystem.data.model.Category;
import com.juls.lab.productmanagementsystem.data.model.Product;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product management API")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;



    /**
     * Retrieves a paginated and optionally sorted list of products from the system.
     *
     * <p>This endpoint supports pagination using the {@code page} and {@code size} parameters,
     * and allows sorting by a specific field and order using the {@code sortBy} and {@code sortOrder}
     * query parameters. If no products are found, the server returns a {@code 204 No Content} response.
     *
     * @param page      the page number to retrieve, starting from 0 (default: 0)
     * @param size      the number of products per page (default: 10, must be a positive integer)
     * @param sortBy    the field to sort the products by (default: "id")
     * @param sortOrder the sorting order ("asc" for ascending or "desc" for descending; default: "asc")
     *
     * @return a {@link ResponseEntity} containing a paginated list of products. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with a list of products when results are found</li>
     *             <li> {@code 204 No Content} if no products match the requested parameters</li>
     *         </ul>
     * @throws IllegalArgumentException if the {@code sortOrder} is invalid or {@code size} is non-positive
     *
     * @see ProductService#getAllProducts(Pageable)
     * @see Pageable
     * @see Sort
     */

    @Operation(
            summary = "Get all products with pagination and sorting",
            description = "Retrieves a paginated and optionally sorted list of products from the system.",
            parameters = {
                    @Parameter(name = "page", description = "The page number to retrieve, starting from 0", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "The number of products per page", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "integer", defaultValue = "10")),
                    @Parameter(name = "sortBy", description = "The field to sort the products by", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "string", defaultValue = "id")),
                    @Parameter(name = "sortOrder", description = "The sorting order, either 'asc' or 'desc'", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "string", defaultValue = "asc"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of products", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "204", description = "No content found for the given parameters"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
            }
    )

    @GetMapping("/all")


    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                        @RequestParam(defaultValue = "10") @Min(1) int size,
                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Product> products = this.productService.getAllProducts(pageable);

        return products.hasContent() ? ResponseEntity.ok(products) : ResponseEntity.noContent().build();
    }



    /**
     * Retrieves a list of products within the specified price range.
     *
     * <p>This endpoint allows the user to filter products by a price range defined by {@code minPrice} and {@code maxPrice}.
     * The request will return all products whose price is between the given range, inclusive. If no products are found,
     * a {@code 204 No Content} response is returned. If the price range is invalid (i.e., {@code minPrice} is greater than
     * {@code maxPrice}), a {@code 400 Bad Request} response is returned.
     *
     * @param minPrice the minimum price of the products to retrieve (must be greater than or equal to 0)
     * @param maxPrice the maximum price of the products to retrieve (must be greater than or equal to 0)
     *
     * @return a {@link ResponseEntity} containing a list of products within the specified price range. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with the list of products when products are found</li>
     *             <li> {@code 204 No Content} if no products match the specified price range</li>
     *             <li> {@code 400 Bad Request} if the {@code minPrice} is greater than {@code maxPrice}</li>
     *         </ul>
     *
     * @throws IllegalArgumentException if {@code minPrice} or {@code maxPrice} is less than 0
     *
     * @see ProductService#getProductsByPriceRange(double, double)
     */
    @Operation(
            summary = "Get products by price range",
            description = "Retrieves a list of products that fall within the specified price range, inclusive. Returns a " +
                    "bad request if the minimum price is greater than the maximum price.",
            parameters = {
                    @Parameter(name = "minPrice", description = "The minimum price of the products to retrieve", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "number", format = "double", minimum = "0")),
                    @Parameter(name = "maxPrice", description = "The maximum price of the products to retrieve", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "number", format = "double", minimum = "0"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of products within the price range", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "204", description = "No content found for the given price range"),
                    @ApiResponse(responseCode = "400", description = "Invalid price range (minPrice is greater than maxPrice)")
            }
    )
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam @Min(0) double minPrice,
            @RequestParam @Min(0) double maxPrice) {
        if (minPrice > maxPrice) {
            return ResponseEntity.badRequest().body(null); // Invalid price range
        }
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }



    /**
     * Retrieves a list of products that belong to the specified category.
     *
     * <p>This endpoint fetches all products associated with a specific category by the provided {@code categoryId}.
     * If no products are found for the given category, a {@code 204 No Content} response is returned. If the category
     * is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param categoryId the ID of the category to retrieve products from
     *
     * @return a {@link ResponseEntity} containing a list of products within the specified category. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with the list of products if products are found</li>
     *             <li> {@code 204 No Content} if no products are found for the given category</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the category with the specified {@code categoryId} is not found
     *
     */
    @Operation(
            summary = "Get products by category",
            description = "Fetches all products that belong to the specified category by {@code categoryId}. If no products are found, returns a 204 No Content response.",
            parameters = {
                    @Parameter(name = "categoryId", description = "The ID of the category to retrieve products from", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products found for the category", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "204", description = "No content found for the specified category"),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content())
            }
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable Long categoryId) throws ResourceNotFoundException {
        List<Product> productList = this.productService.getProductsByByCategory(categoryId);
        return productList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productList);
    }

    /**
     * Searches for products based on a provided keyword.
     *
     * <p>This endpoint allows searching for products by a keyword. The keyword can be matched with any field (e.g.,
     * product name, description, etc.). If no products are found matching the keyword, a {@code 204 No Content} response
     * is returned.
     *
     * @param keyword the search keyword to find matching products
     *
     * @return a {@link ResponseEntity} containing a list of products that match the search keyword. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with the list of products that match the keyword</li>
     *             <li> {@code 204 No Content} if no products match the keyword</li>
     *         </ul>
     *
     * @see ProductService#searchProducts(String)
     */
    @Operation(
            summary = "Search products by keyword",
            description = "Search for products by keyword. The keyword can be matched with any field such as product name, description, etc.",
            parameters = {
                    @Parameter(name = "keyword", description = "The keyword used to search for products", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products that match the search keyword", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "204", description = "No content found for the given search keyword")
            }
    )
    @GetMapping("/search")

    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }


    /**
     * Deactivates a product by its ID.
     *
     * <p>This endpoint deactivates the product with the specified {@code id}. The product will be marked as inactive, and
     * it will no longer be available for purchase. A {@code 200 OK} response is returned if the product is successfully deactivated.
     * If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to deactivate
     *
     * @return a {@link ResponseEntity} indicating the success of the operation. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} if the product was successfully deactivated</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#deactivateProduct(Long)
     */
    @Operation(
            summary = "Deactivate product",
            description = "Deactivates the product with the specified {@code id}, making it unavailable for purchase.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to deactivate", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product successfully deactivated"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Activates a product by its ID.
     *
     * <p>This endpoint activates the product with the specified {@code id}. A {@code 200 OK} response is returned
     * once the product is successfully activated. If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to activate
     *
     * @return a {@link ResponseEntity} indicating the success of the operation. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} if the product was successfully activated</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#activateProduct(Long)
     */
    @Operation(
            summary = "Activate product",
            description = "Activates the product with the specified {@code id}, making it available for purchase.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to activate", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product successfully activated"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PatchMapping("/{id}/activate")

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> activateProduct(@PathVariable Long id) {
        productService.activateProduct(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a new attribute to the specified product.
     *
     * <p>This endpoint allows adding a new attribute (name and value) to an existing product with the given {@code id}.
     * If the attribute is successfully added, a {@code 201 Created} response is returned. The product should already
     * exist in the system, otherwise, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to add the attribute to
     * @param name the name of the attribute to add
     * @param value the value of the attribute to add
     *
     * @return a {@link ResponseEntity} indicating the success of the operation. The response includes:
     *         <ul>
     *             <li> {@code 201 Created} if the attribute was successfully added</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#addNewAttribute(Long, String, String)
     */
    @Operation(
            summary = "Add new attribute to product",
            description = "Adds a new attribute (name and value) to the product with the specified {@code id}.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to add the attribute to", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64")),
                    @Parameter(name = "name", description = "The name of the attribute to add", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "value", description = "The value of the attribute to add", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string"))
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Attribute successfully added to product"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PostMapping("/{id}/attributes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addNewAttribute(
            @PathVariable Long id,
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String value) {
        productService.addNewAttribute(id, name, value);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a product by its ID.
     *
     * <p>This endpoint deletes the product with the specified {@code id}. A {@code 204 No Content} response is returned
     * once the product is successfully deleted. If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to delete
     *
     * @return a {@link ResponseEntity} indicating the success of the operation. The response includes:
     *         <ul>
     *             <li> {@code 204 No Content} if the product was successfully deleted</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#deleteProduct(Long)
     */
    @Operation(
            summary = "Delete product",
            description = "Deletes the product with the specified {@code id}.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to delete", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the stock quantity of a product.
     *
     * <p>This endpoint updates the stock quantity of a product with the specified {@code id}. A {@code 200 OK} response is returned
     * once the stock is successfully updated. If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to update
     * @param quantity the new stock quantity to set for the product
     *
     * @return a {@link ResponseEntity} indicating the success of the operation. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} if the stock was successfully updated</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#updateProductStock(Long, Integer)
     */
    @Operation(
            summary = "Update product stock",
            description = "Updates the stock quantity for the product with the specified {@code id}.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to update", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64")),
                    @Parameter(name = "quantity", description = "The new stock quantity to set", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "integer", format = "int32"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product stock successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PatchMapping("/{id}/stock")

    public ResponseEntity<Void> updateProductStock(
            @PathVariable Long id,
            @RequestParam @Min(1) Integer quantity) throws ResourceNotFoundException {
        productService.updateProductStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks if a product is in stock.
     *
     * <p>This endpoint checks whether a product with the specified {@code id} is currently in stock.
     * A {@code true} value is returned if the product is in stock, and {@code false} if not.
     * If the product is not found, a {@code ProductNotFoundException} is thrown.
     *
     * @param id the ID of the product to check
     *
     * @return a {@link ResponseEntity} containing a boolean value indicating whether the product is in stock.
     *         <ul>
     *             <li> {@code 200 OK} with {@code true} if the product is in stock</li>
     *             <li> {@code 200 OK} with {@code false} if the product is out of stock</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ProductNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#isProductInStock(Long)
     */
    @Operation(
            summary = "Check if product is in stock",
            description = "Checks if the product with the specified {@code id} is currently in stock.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to check", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product stock status returned", content = @Content(schema = @Schema(type = "boolean"))),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @GetMapping("/{id}/stock")
    public ResponseEntity<Boolean> isProductInStock(@PathVariable Long id) throws ProductNotFoundException {
        boolean inStock = productService.isProductInStock(id);
        return ResponseEntity.ok(inStock);
    }

    /**
     * Retrieves a product by its ID.
     *
     * <p>This endpoint fetches the details of a product based on the provided {@code id}.
     * If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to retrieve
     *
     * @return a {@link ResponseEntity} containing the product details. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with the product details if found</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#getProductById(Long)
     */
    @Operation(
            summary = "Get product by ID",
            description = "Retrieves the details of the product with the specified {@code id}.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to retrieve", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product details returned", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Updates the details of an existing product.
     *
     * <p>This endpoint updates the details of a product with the specified {@code id}.
     * A {@code 200 OK} response is returned once the product is successfully updated.
     * If the product is not found, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the product to update
     * @param product the updated product details
     *
     * @return a {@link ResponseEntity} containing the updated product. The response includes:
     *         <ul>
     *             <li> {@code 200 OK} with the updated product if the product was found and updated</li>
     *             <li> {@code 404 Not Found} if the product with the specified {@code id} does not exist</li>
     *         </ul>
     *
     * @throws ResourceNotFoundException if the product with the specified {@code id} does not exist
     *
     * @see ProductService#updateProduct(Long, Product)
     */
    @Operation(
            summary = "Update product",
            description = "Updates the details of the product with the specified {@code id}.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the product to update", in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer", format = "int64")),
                    @Parameter(name = "product", description = "The updated product details", in = ParameterIn.COOKIE, required = true, schema = @Schema(implementation = Product.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product successfully updated", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) throws ResourceNotFoundException {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new product.
     *
     * <p>This endpoint creates a new product with the provided details.
     * A {@code 201 Created} response is returned once the product is successfully created.
     *
     * @param product the product details to create
     *
     * @return a {@link ResponseEntity} containing the created product. The response includes:
     *         <ul>
     *             <li> {@code 201 Created} with the created product details</li>
     *         </ul>
     *
     * @see ProductService#createProduct(Product)
     */
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided details.",

            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(schema = @Schema(implementation = Product.class)))
            }
    )
    @PostMapping
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO product) {
        Product newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantityInStock(product.getQuantityInStock());
        Category category = categoryService.getCategoryById(product.getCategoryId());
        newProduct.setDiscount(BigDecimal.valueOf(product.getDiscount()));
        newProduct.setCategory(category);

        Product createdProduct = productService.createProduct(newProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);


    }
}
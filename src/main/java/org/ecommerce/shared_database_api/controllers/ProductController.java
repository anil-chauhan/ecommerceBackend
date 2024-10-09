package org.ecommerce.shared_database_api.controllers;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.dto.ProductRequestParamDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
//@CrossOrigin(value = "http://localhost:4200")
public class ProductController {


    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productService,CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }




    @Data
    class ProductResponseMessage{
        String message;
    }


    @PostMapping("/add_product")
    public ProductResponseMessage addProduct(
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            //@RequestParam("urlSlug") String urlSlug,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam("status") String status,
            @RequestParam("brand") String brand,
            @RequestParam("categoryName") String categoryName) {

        // Create Product object and set values
        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setUrlSlug("");
        product.setStockQuantity(stockQuantity);
        product.setStatus(status);
        product.setBrand(brand);

        // Handle image upload
        if (!productImage.isEmpty()) {
            // Save the image and get the URL (you need to implement this logic)
            String imageUrl = saveImage(productImage);
            product.setProductImageUrl(imageUrl);
        }

        // Set category
        Category categoryById = categoryService.getCategoryByName(categoryName);
        product.setCat(categoryById);

        String s = productService.addProduct(product);
        ProductResponseMessage responseMessage = new ProductResponseMessage();
        responseMessage.message = s;
        // Save product
        return responseMessage;
    }

    private String saveImage(MultipartFile image) {
        // Implement the logic to save the image and return the image URL
        // This can include saving the image to a file system or a cloud storage and returning the accessible URL.

        // Define the directory where images will be saved
        String directory = "C:\\Users\\codel\\Desktop\\ecommerce\\ecommerce-luv2shop-angular-springboot-main\\ecommerce-luv2shop-angular-springboot-main\\products_image\\";

        // Ensure the directory exists
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Generate a random name for the image
        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String randomName = UUID.randomUUID().toString() + fileExtension;

        // Define the full path where the image will be saved
        Path filePath = Paths.get(directory + randomName);

        try {
            // Save the image to the specified path
            Files.copy(image.getInputStream(), filePath);
            // Return the URL to access the image
            //return "file:///" + filePath.toString(); // Local file URL
            return filePath.toString(); // Local file URL
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately, return null or throw a custom exception
            return null;
        }

    }





    @GetMapping("/get_all_product_from_a_category_by_id")
    //@PostAuthorize("hasRole('ADMIN')")
    public List<ProductDto> getAllProductFromACategoryById( @RequestBody ProductDto productDto) {

        Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());

        return productService.getAllProductByCategoryId(categoryById);
    }

    @PostMapping("/get_all_product_from_a_category_by_name")
    //@PostAuthorize("hasRole('ADMIN')")
    public Optional<Object> getAllProductFromACategoryByName(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.getCategoryByName(categoryDto.getCategoryName());
        // find if any subcategory is available or not
        if(category.getParentCat() == null) {
            List<Category> subCategoryByParent = categoryService.getSubCategoryByParentId(category.getCategoryId());
            if(subCategoryByParent!=null){
                return Optional.ofNullable(subCategoryByParent);
            }
            //Category parentCat = category.getParentCat();
            //return Optional.ofNullable(parentCat);

        }
        return Optional.ofNullable(productService.getAllProductByCategoryId(category));
    }




    @PostMapping("/get_all_product_by_name")
    public Page<ProductDto> getAllProductByName(@RequestBody ProductRequestParamDto productRequestParamDto) {


        Integer pageNumber = productRequestParamDto.getPageNumber();
        Integer pageSize = productRequestParamDto.getPageSize();
        String productName = productRequestParamDto.getProductName();
        if(pageSize==0){
            pageSize=10;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        // Using the provided Pageable instead of creating a new instance
        Page<ProductDto> allProductByName = productService.getAllProductByName(productName, pageable);
        return allProductByName;
    }


    @PostMapping("/get_all_product")
    public Page<ProductDto> getAllProduct(@RequestBody ProductRequestParamDto productRequestParamDto) {


        Integer pageNumber = productRequestParamDto.getPageNumber();
        Integer pageSize = productRequestParamDto.getPageSize();
        //String productName = productRequestParamDto.getProductName();
        if(pageSize==0){
            pageSize=10;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        // Using the provided Pageable instead of creating a new instance
        Page<ProductDto> allProductByName = productService.getAllProducts( pageable);
        return allProductByName;
    }


    @PostMapping("/get_all_trendy_product")
    public ArrayList<ProductDto> getAllTrendyProduct() {
        ArrayList<ProductDto> allProductByName = productService.getAllTrendyProducts();
        return allProductByName;
    }


    @Data
    @Getter
    @Setter
    public static class ProductDetailsRequest{
        int productId;
    }


    @PostMapping("/get_product_by_id")
    public ProductDto getProductById(@RequestBody() ProductDetailsRequest productDetailsRequest) {

        Integer productId = productDetailsRequest.getProductId();


        // Using the provided Pageable instead of creating a new instance
        ProductDto allProductByName = productService.getProductById(productId);
        return allProductByName;
    }


}

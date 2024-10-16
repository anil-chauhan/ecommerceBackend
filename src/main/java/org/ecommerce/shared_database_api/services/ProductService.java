package org.ecommerce.shared_database_api.services;

import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.ecommerce.shared_database_api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Page;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    //private final CategoryService categoryService;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        //this.categoryService = categoryService;
    }


    public  String addProduct(Product product){

        Product save = productRepository.save(product);
        if(save != null){
            return "Product add successfully";
        }
        else {
            return "Product addition failed";
        }

    }

    public  Product getProductById(int productId){

        Product save = productRepository.findProductByProductId(productId);
        if(save!=null){
            return save;
        }
        else {
            return null;
        }



    }

    public  List<ProductDto> getAllProductByCategoryId(Category category){




        List<Product> save = productRepository.findProductByCategoryId(category.getCategoryId());

        List<ProductDto> productDtos = new ArrayList<>();
        if(save!=null){

            for(Product product : save){

                ProductDto productDto=new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                //Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
                //productDto.setCat(categoryById);
                productDtos.add(productDto);
            }



            return productDtos;
        }
        else {
            return null;
        }



    }



    public  ArrayList<ProductDto> getAllProductByListCategoriesId(List<Integer> categoryIdList){

        ArrayList<Product> mainList=new ArrayList<>();

        for (Integer categoryId:categoryIdList) {

            List<Product> save = productRepository.findProductByCategoryId(categoryId);
            mainList.addAll(save);

        }






        ArrayList<ProductDto> productDtos = new ArrayList<>();
        if(mainList!=null){

            for(Product product : mainList){

                ProductDto productDto=new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                //Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
                //productDto.setCat(categoryById);
                productDtos.add(productDto);
            }



            return productDtos;
        }
        else {
            return null;
        }



    }




    public  List<ProductDto> getAllProductCountByCategoryId(Integer categoryId){


        List<Product> products = productRepository.findProductByCategoryId(categoryId);

        List<ProductDto> productDtos = new ArrayList<>();
        if(products!=null){

            for(Product product : products){

                ProductDto productDto=new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                //Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
                //productDto.setCat(categoryById);
                productDtos.add(productDto);
            }



            return productDtos;
        }
        else {
            return null;
        }



    }


    public String convertImageToBase64(String imagePath) {
        try {



            // Read the image file into a byte array
            File imageFile = new File(imagePath);
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

            // Encode the byte array to a Base64 string
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Return the Base64 string prefixed with the appropriate data type
            return "data:image/png;base64," + base64Image; // Adjust the MIME type if necessary
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error appropriately
        }
    }



    public Page<ProductDto> getAllProductByName(String productName, Pageable pageable) {
        // Get paginated products by name
        Page<Product> productByName = productRepository.findProductByName(productName, pageable);


        List<ProductDto> productDtos = new ArrayList<>();
        if (productByName != null) {
            for (Product product : productByName) {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                //Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
                //productDto.setCat(categoryById);
                productDtos.add(productDto);
            }
        }
        return new PageImpl<>(productDtos, pageable, productByName.getTotalElements());
    }


    public Page<ProductDto> getAllProducts(Pageable pageable) {
        // Get paginated products by name
        Page<Product> productByName = productRepository.findProducts( pageable);


        List<ProductDto> productDtos = new ArrayList<>();
        if (productByName != null) {
            for (Product product : productByName) {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                //Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
                //productDto.setCat(categoryById);
                productDtos.add(productDto);
            }
        }
        return new PageImpl<>(productDtos, pageable, productByName.getTotalElements());
    }



    public ArrayList<ProductDto> getAllTrendyProducts() {
        // Get paginated products by name
        ArrayList<Product> productByName = productRepository.findTrendyProducts( );


        ArrayList<ProductDto> productDtos = new ArrayList<>();
        if (productByName != null) {
            for (Product product : productByName) {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());
                productDtos.add(productDto);
            }
        }
        return productDtos;
    }

    public  ProductDto getProductById(Integer  productId){

        Product product = productRepository.findProductByProductId(productId);
        if(product!=null){
                ProductDto productDto=new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
                String s = convertImageToBase64(product.getProductImageUrl());
                productDto.setProductImageUrl(s);
                productDto.setCategoryId(product.getCat().getCategoryId());

            return productDto;
        }
        else {
            return null;
        }



    }



}

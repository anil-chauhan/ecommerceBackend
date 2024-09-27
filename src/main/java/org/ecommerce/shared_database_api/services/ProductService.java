package org.ecommerce.shared_database_api.services;

import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.ecommerce.shared_database_api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    @Autowired
    public ProductService(ProductRepository productRepository,CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
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
                productDto.setProductName(product.getProductName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setUrlSlug(product.getUrlSlug());
                productDto.setDescription(product.getDescription());
                productDto.setStockQuantity(product.getStockQuantity());
                productDto.setStatus(product.getStatus());
                productDto.setBrand(product.getBrand());
                productDto.setProductImageUrl(product.getProductImageUrl());
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
}

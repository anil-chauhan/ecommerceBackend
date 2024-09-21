package org.ecommerce.shared_database_api.services;

import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.ecommerce.shared_database_api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        Product save = productRepository.findProductByCategoryId(productId);
        if(save!=null){
            return save;
        }
        else {
            return null;
        }



    }
}

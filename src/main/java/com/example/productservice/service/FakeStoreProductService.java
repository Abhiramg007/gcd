package com.example.productservice.service;

import com.example.productservice.DTO.FakeStoreProductDTO;
import com.example.productservice.DTO.ProductDTO;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProduct(Integer id) {
        Product product = new Product();
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductDTO.class);
        FakeStoreProductDTO fakeStoreProductDTO = response.getBody();

        product = convertFakeStoreToProduct(fakeStoreProductDTO);
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        ResponseEntity<FakeStoreProductDTO[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
        for (FakeStoreProductDTO fakeStoreProductDTO : response.getBody()) {
            products.add(convertFakeStoreToProduct(fakeStoreProductDTO));
        }

        return products;
    }

    Product convertFakeStoreToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        Category category = new Category();

        product.setId(fakeStoreProductDTO.getId());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setImageURL(fakeStoreProductDTO.getImage());
        category.setName(fakeStoreProductDTO.getCategory());
        product.setCategory(category);

        return product;
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product;

        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setDescription(productDTO.getDescription());
        fakeStoreProductDTO.setTitle(productDTO.getTitle());
        fakeStoreProductDTO.setImage(productDTO.getImageURL());
        fakeStoreProductDTO.setCategory(productDTO.getCategory().getName());

        ResponseEntity<FakeStoreProductDTO> response = restTemplate.postForEntity
                ("https://fakestoreapi.com/products", fakeStoreProductDTO, FakeStoreProductDTO.class);

        product = convertFakeStoreToProduct(response.getBody());
        return product;
    }

    public Product updateProduct(Integer id,ProductDTO productDTO) {
        Product product;

        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setDescription(productDTO.getDescription());
        fakeStoreProductDTO.setTitle(productDTO.getTitle());
        fakeStoreProductDTO.setImage(productDTO.getImageURL());
        fakeStoreProductDTO.setCategory(productDTO.getCategory().getName());

        String url="https://fakestoreapi.com/products/"+id;
        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(fakeStoreProductDTO);
        ResponseEntity<FakeStoreProductDTO> response=restTemplate.exchange
                (url, HttpMethod.PUT,request,FakeStoreProductDTO.class);

        product = convertFakeStoreToProduct(response.getBody());
        return product;
    }

    public String deleteProduct(Integer id) {

        restTemplate.delete("https://fakestoreapi.com/products/"+id);

        return "Product deleted";

    }
}


package sample.cafekiosk.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import sample.cafekiosk.api.service.product.response.ProductResponse;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.product.ProductSellingType;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}

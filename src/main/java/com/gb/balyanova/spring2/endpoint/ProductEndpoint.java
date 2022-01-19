package com.gb.balyanova.spring2.endpoint;

import com.gb.balyanova.spring2.services.ProductService;
import com.gb.balyanova.spring2.ws.products.GetAllProductsRequest;
import com.gb.balyanova.spring2.ws.products.GetAllProductsResponse;
import com.gb.balyanova.spring2.ws.products.GetProductByIdRequest;
import com.gb.balyanova.spring2.ws.products.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.gb.balyanova.com/spring2/ws/products";
    private final ProductService productService;

        /*
        POST http://localhost:8189/app/ws/products

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:p="http://www.gb.balyanova.com/spring2/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <p:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAllSoap().forEach(response.getProducts()::add);
        return response;
    }

/*  POST http://localhost:8189/app/ws/products
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:p="http://www.gb.balyanova.com/spring2/ws/products" >
            <soapenv:Header/>
            <soapenv:Body>
                <p:getProductByIdRequest>
                    <p:id>1</p:id>
                </p:getProductByIdRequest>
            </soapenv:Body>
        </soapenv:Envelope>
 */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.findByIdSoap(request.getId()));
        return response;
    }
}

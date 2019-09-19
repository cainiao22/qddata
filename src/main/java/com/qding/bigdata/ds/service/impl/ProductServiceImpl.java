package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.ProductMapper;
import com.qding.bigdata.ds.model.Product;
import com.qding.bigdata.ds.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 17:21
 * @description
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        return productMapper.getAll();
    }
}

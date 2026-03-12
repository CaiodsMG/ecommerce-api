package com.ecommerce.controller;

import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.entity.Produto;
import com.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired // Injeção de Dependência
    private ProdutoService service;

    @GetMapping
    public List<ProdutoDTO> listarProdutos(){
        return service.listarProdutos();
    }

    @GetMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO procurarPorId(@PathVariable Long produtoId){
        return service.procurarProdutoPorId(produtoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO criarProduto(@Valid @RequestBody Produto produto){
        return service.criarProduto(produto);
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO atualizarProduto(@PathVariable Long produtoId,
                                    @Valid @RequestBody Produto produto){
        return service.atualizarProduto(produtoId, produto);
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long produtoId){
        service.deletarProduto(produtoId);
    }
}

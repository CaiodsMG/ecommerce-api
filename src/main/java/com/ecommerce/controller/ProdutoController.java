package com.ecommerce.controller;

import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.entity.Produto;
import com.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired // Injeção de Dependência
    private ProdutoService service;

    @GetMapping("/listar")
    public List<ProdutoDTO> listarProdutos(){
        return service.listarProdutos();
    }

    @GetMapping("/listarPorTamanho")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProdutoDTO> listarProdutos(Pageable pageable){

        Page<ProdutoDTO> produtos = service.listarProdutosPorPaginas(pageable);

        return produtos;
    }

    @GetMapping("/procurar/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO procurarPorId(@PathVariable Long produtoId){
        return service.procurarProdutoPorId(produtoId);
    }

    @PostMapping("/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO criarProduto(@Valid @RequestBody ProdutoDTO produto){
        return service.criarProduto(produto);
    }

    @PutMapping("/atualizar/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO atualizarProduto(@PathVariable Long produtoId,
                                    @Valid @RequestBody Produto produto){
        return service.atualizarProduto(produtoId, produto);
    }

    @DeleteMapping("/deletar/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long produtoId){
        service.deletarProduto(produtoId);
    }
}

package com.ecommerce.service;

import com.ecommerce.entity.Produto;
import com.ecommerce.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired //Injeção de dependência
    private ProdutoRepository repository;

    //Criação de um produto
    public Produto criarProduto(Produto produto){
        return repository.save(produto);
    }

    //Listar todos os produtos
    public List<Produto> listarProdutos(){
        return repository.findAll();
    }

    //Procura o produto pelo id informado, se o id não existir retornará uma mensagem.
    public Produto procurarProdutoPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O produto com o id: " + id + " não existe."));
    }

    //Procura o produto pelo id informado e pede um corpo para a atualização.
    public Produto atualizarProduto(Long id, Produto produto){
        Produto produtoAtualizado = procurarProdutoPorId(id);

        BeanUtils.copyProperties(produto, produtoAtualizado, "id");

        return repository.save(produtoAtualizado);
    }

    //Deleta o produto pelo id
    public void deletarProduto(Long id){
        repository.deleteById(id);
    }
}

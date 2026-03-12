package com.ecommerce.service;

import com.ecommerce.dto.ProdutoDTO;
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
    public ProdutoDTO criarProduto(Produto produto){
        Produto produtoCriado = repository.save(produto);

        return toDTO(produtoCriado);
    }

    //Listar todos os produtos
    public List<ProdutoDTO> listarProdutos(){
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    //Procura o produto pelo id informado, se o id não existir retornará uma mensagem.
    public ProdutoDTO procurarProdutoPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O produto com o id: " + id + " não existe."));

        return toDTO(produto);
    }

    //Procura o produto pelo id informado e pede um corpo para a atualização.
    public ProdutoDTO atualizarProduto(Long id, Produto produto) {

        Produto produtoAtualizado = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "O produto com o id: " + id + " não existe."
                ));
        BeanUtils.copyProperties(produto, produtoAtualizado, "id");

        Produto savedProduct = repository.save(produtoAtualizado);

        return toDTO(savedProduct);
    }

    //Deleta o produto pelo id
    public void deletarProduto(Long id){
        repository.deleteById(id);
    }

    private ProdutoDTO toDTO(Produto produto) {

        ProdutoDTO dto = new ProdutoDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setEstoque(produto.getEstoque());

        return dto;
    }
}

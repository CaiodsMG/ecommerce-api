package com.ecommerce.service;

import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.entity.Produto;
import com.ecommerce.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired //Injeção de dependência
    private ProdutoRepository repository;

    //Criação de um produto
    public ProdutoDTO criarProduto(ProdutoDTO dto){
        Produto produto = toEntity(dto);
        Produto produtoCriado = repository.save(produto);
        return toDTO(produtoCriado);
    }

    //Listar todos os produtos
    public List<ProdutoDTO> listarProdutos(){
        return repository.findAll().stream().map(produto -> toDTO(produto)).toList();
    }

    public Page<ProdutoDTO> listarProdutosPorPaginas(Pageable pageable){
        Page<Produto> produtos = repository.findAll(pageable);

        return produtos.map(produto -> toDTO(produto));
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
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "O produto com o id: " + id + " não existe."
                ));

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

    private Produto toEntity(ProdutoDTO dto) {

        Produto produto = new Produto();

        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());

        return produto;
    }
}

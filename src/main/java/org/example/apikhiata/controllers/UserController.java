package org.example.apikhiata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.apikhiata.models.User;
import org.example.apikhiata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final Validator validator;

    @Autowired
    public UserController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    //selecionar todos
    @GetMapping("/selecionar")
    @Operation(summary = "Busca por todos os usuários", description = "Busca todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public List<User> buscarTodos() {
        return userService.findAllUsers();
    }

    @GetMapping("/selecionar/nome/{nome}")
    @Operation(summary = "busca usuário por nome", description = "Busca o usuário selecionado pelo nome dele")
    public ResponseEntity<?> buscarUsuarioPorNome(@Valid @PathVariable String nome) {
        List<User> userList = userService.findUserByName(nome);
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clientes não encontrado");
        } else {
            return ResponseEntity.ok(userList);
        }

    }

    @GetMapping("/selecionar/email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Retornar um cliente pelo seu email")
    public ResponseEntity<?> buscarClientePorEmail(@Valid @PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/selecionar/id/{id}")
    @Operation(summary = "Buscar cliente por id", description = "Retornar um cliente pelo seu id")
    public ResponseEntity<?> buscarClientePorId(@Valid @PathVariable int id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserir(@Valid @RequestBody User user) {
        try {
            // Salva o novo usuário
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cliente inserido com sucesso");
        } catch (ConstraintViolationException e) {
            // Lida com erro de validação
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            // Lida com outros tipos de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao inserir o usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar usuário por id", description = "Deleta um usuário pelo id dele")
    public ResponseEntity<String> deletar(@Valid @PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
        } catch (RuntimeException e) {
            // Retorna 404 Not Found se o usuário não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Lida com outros tipos de erro, retornando 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o usuário: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar usuário por id", description = "Atualiza um usuário existente pelo seu id")
    public ResponseEntity<String> atualizarUsuario(@Valid @PathVariable int id, @Valid @RequestBody User user) {
        try {
            // Chama o serviço para atualizar o usuário
            User usuarioAtualizado = userService.updateUser(id, user);
            return ResponseEntity.ok("Usuário atualizado com sucesso: " + usuarioAtualizado.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

}//fim do controller

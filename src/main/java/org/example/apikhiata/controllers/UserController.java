package org.example.apikhiata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.apikhiata.models.User;
import org.example.apikhiata.models.UserPreferencesDTO;
import org.example.apikhiata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/selecionar/awaiting-premium")
    @Operation(summary = "Busca clientes pendentes para aprovação", description = "Retornar todos os clientes que estão pendentes para aprovação do premium")
    public ResponseEntity<?> buscarTodosPendentes() {
        List<User> userList = userService.findUsersAwaitingPremiumApproval();
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente encontrado");
        } else {
            return ResponseEntity.ok(userList);
        }
    }

    @PostMapping(value = "/inserir")
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

    @DeleteMapping("/deletar/email/{email}")
    @Operation(summary = "Deletar usuário por email", description = "Deleta um usuário pelo email dele")
    public ResponseEntity<String> deletarPorEmail(@Valid @PathVariable String email) {
        try {
            userService.deleteUserByEmail(email);
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


    @PatchMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar parcialmente o usuário por ID", description = "Atualiza apenas os campos mandados do usuário pelo seu ID")
    public ResponseEntity<?> atualizarParcial(@PathVariable int id, @RequestBody Map<String, Object> atualizacoes) {
        try {
            User usuarioAtualizado = userService.updatePartialUserWithId(id, atualizacoes);
            //caso de sucesso
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário atualizado com sucesso");
            response.put("usuario", usuarioAtualizado);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Retorna 404 Not Found se o usuário não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            //Qualquer outro erro vai mandar 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

    @PatchMapping("/atualizar/email/{email}")
    @Operation(summary = "Atualizar parcialmente o usuário por email", description = "Atualiza apenas os campos mandados do usuário pelo seu email")
    public ResponseEntity<?> atualizarParcial(@PathVariable String email, @RequestBody Map<String, Object> atualizacoes) {
        try {
            User usuarioAtualizado = userService.updatePartialUserWithEmail(email, atualizacoes);
            //caso de sucesso
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário atualizado com sucesso");
            response.put("usuario", usuarioAtualizado);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Retorna 404 Not Found se o usuário não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            //Qualquer outro erro vai mandar 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

    @PatchMapping("/atualizar-preferencias/{userId}")
    @Operation(summary = "Atualizar preferências do usuário", description = "Atualiza as preferências do usuário especificado pelo ID")
    public ResponseEntity<?> atualizarPreferencias(@PathVariable int userId, @RequestBody UserPreferencesDTO userPreferencesDTO) {
        try {
            User usuarioAtualizado = userService.updateUserPreferences(userId, userPreferencesDTO.getUserPreferences());

            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Preferências do usuário atualizadas com sucesso");
            response.put("usuario", usuarioAtualizado);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Retorna 404 Not Found se o usuário não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Qualquer outro erro retornará 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar as preferências do usuário: " + e.getMessage());
        }
    }

}//fim do controller

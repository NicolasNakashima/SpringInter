package org.example.apikhiata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.apikhiata.models.Address;
import org.example.apikhiata.models.User;
import org.example.apikhiata.services.AddressService;
import org.example.apikhiata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ControllerAdvice
@RestController
@RequestMapping("/api/enderecos")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    private UserService userService;

    @GetMapping("/selecionar")
    @Operation(summary = "Busca por todos os endereços", description = "Busca todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public List<Address> buscarTodos() {
        return addressService.findAllAddresses();
    }

    @GetMapping("/selecionar/id/{id}")
    @Operation(summary = "Buscar endereço por id", description = "Retornar um endereço pelo seu id")
    public ResponseEntity<?> buscarEnderecoPorId(@Valid @PathVariable int id) {
        try {
            Address address = addressService.findAddressById(id);
            return ResponseEntity.ok(address);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserir(@Valid @RequestBody Address address) {
        try {
            // Salva o novo usuário
            addressService.saveAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Endereço inserido com sucesso");
        } catch (ConstraintViolationException e) {
            // Lida com erro de validação
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            // Lida com outros tipos de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao inserir o endereço: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um endereço por id", description = "Deleta um endereço pelo id dele")
    public ResponseEntity<String> deletar(@Valid @PathVariable int id) {
        try {
            addressService.deleteAddressById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado com sucesso");
        } catch (RuntimeException e) {
            // Retorna 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Retorna 500 caso ocorra outros erros
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o endereço: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar endereço por id", description = "Atualiza um endereço existente pelo seu id")
    public ResponseEntity<String> atualizarEndereco(@Valid @PathVariable int id, @Valid @RequestBody Address address) {
        try {
            // Chama o serviço para atualizar o endereço
            Address updateAddress = addressService.updateAddressById(id, address);
            return ResponseEntity.ok("Endereço atualizado com sucesso: " + updateAddress.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o endereço: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<Address>> getAddressesByUserId(@PathVariable int userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            Set<Address> addresses = user.getAddresses();
            return ResponseEntity.ok(addresses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/inserir/{userId}")
    public ResponseEntity<Address> addAddressToUser(@PathVariable int userId, @RequestBody Address address) {
        User user = userService.findUserById(userId);

        if (user != null) {
            addressService.saveAddress(address);
            user.getAddresses().add(address);
            userService.saveUser(user);
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}//fim do controller

package org.example.apikhiata.controllers;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.apikhiata.models.PasswordUtils;
import org.example.apikhiata.models.User;
import org.example.apikhiata.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //Inicializando a chave secreta em tempo de execução
    private final SecretKey secretKey; //Chave secreta segura
    private final UserService userService;
    private final Gson gson = new Gson();
    public AuthController(UserService userService, SecretKey secretKey){
        this.userService = userService;
        this.secretKey = secretKey;
    }


    @PostMapping("/login")
    @Operation(summary = "Gerar token de autenticação", description = "Faz a geração do token JWT de autenticação")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "O token foi gerado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyLnRlc3RlQGdtYWlsLmNvbSIsInJvbGVzIjpbXSwiZXhwIjoxNzMwMjUwMzA2fQ.P_QJxfbLxzOdOdad6nCuiJQbzxBVsmgycu7gMPrLdoOnSGl_W_4VWaPUAGubTyNQVv5v7LqVNlkDXYBBXUKc9A"))),
            @ApiResponse(responseCode = "404", description = "Endpoint não foi encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Endpoint não foi encontrado"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "Erro interno no servidor"))),
    })
    public ResponseEntity<?> login(@RequestBody @Parameter(description = "Dados do usuário administrador para login")
                                       @Schema(description = "Objeto contendo o username e senha do administrador", example = "{\"username\": \"userservice.restrictpage@germinare.org.br\", \"password\": \"restrictpage123\"}") Map<String, String> objectUser) {
        if (objectUser.containsKey("username") && objectUser.containsKey("password")) {
            String username = objectUser.get("username");
            String password = objectUser.get("password");

            Optional<User> user = userService.findUserByName(username);

            if (user.isPresent() && PasswordUtils.checkPassword(password, user.get().getPassword())) {
                try {
                    String token = Jwts.builder()
                            .setSubject(user.get().getName())
                            .claim("roles", Collections.emptyList())
                            .setExpiration(new Date(System.currentTimeMillis() + 180_000)) // Token válido por 3 minutos
                            .signWith(SignatureAlgorithm.HS512, secretKey) //Usa a chave secreta para assinar
                            .compact();

                    Map<String, Object> response = new HashMap<>();
                    response.put("token", token);
                    response.put("url_photo", user.get().getProfilePictureUrl());

                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro ao gerar o Token JWT"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Nome ou senha inválidos"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Valores foram inseridos incorretamente!")) ;
        }
    }

    // Verifica se a senha corresponde ao hash
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
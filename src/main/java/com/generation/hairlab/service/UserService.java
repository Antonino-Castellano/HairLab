package com.generation.hairlab.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.LoginRequestDto;
import com.generation.hairlab.dto.LoginResponseDto;
import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.enums.Role;
import com.generation.hairlab.mapper.UserMapper;
import com.generation.hairlab.model.User;
import com.generation.hairlab.repository.UserRepository;
import com.generation.hairlab.security.JwtService;
import com.generation.hairlab.utility.PasswordService;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione degli utenti HairLab.
 *
 * Gestisce:
 *
 * - ricerca tramite email;
 * - registrazione;
 * - hashing delle password;
 * - cambio password;
 * - login;
 * - generazione JWT;
 * - controllo sicuro del ruolo
 *   durante la registrazione.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repository dedicato agli utenti.
     */
    private final UserRepository userRepo;

    /**
     * Service utilizzato per:
     *
     * - codificare le password;
     * - confrontare password in chiaro e hash.
     */
    private final PasswordService passwordService;

    /**
     * Service dedicato alla generazione
     * e gestione dei JWT.
     */
    private final JwtService jwtService;

    /**
     * Mapper User <-> UserDto.
     */
    private final UserMapper userMapper;

    /**
     * Cerca un utente tramite email.
     *
     * @param email email dell'utente
     * @return utente trovato oppure null
     */
    public User findByEmail(
        String email
    ) {

        return userRepo.findByEmail(
            email
        );
    }

    /**
     * Modifica la password
     * dell'utente autenticato.
     *
     * L'identità dell'utente viene recuperata
     * dal SecurityContext popolato dal JWT.
     *
     * @param password nuova password
     * @throws ServiceException in caso di errore
     */
    public void changePassword(
        String password
    )
    throws ServiceException {

        /*
         * Recuperiamo l'autenticazione
         * corrente da Spring Security.
         */
        Authentication authentication =
            SecurityContextHolder
                .getContext()
                .getAuthentication();

        /*
         * Controllo difensivo.
         *
         * In condizioni normali questo metodo
         * dovrebbe essere richiamato solamente
         * da endpoint protetti.
         */
        if (
            authentication == null ||
            authentication.getName() == null
        ) {

            throw new ServiceException(
                "Utente non autenticato"
            );
        }

        /*
         * Nel JWT abbiamo utilizzato
         * l'email come subject.
         */
        String email =
            authentication.getName();

        User user =
            userRepo.findByEmail(
                email
            );

        if (user == null) {

            throw new ServiceException(
                "User with email "
                + email
                + " not found"
            );
        }

        /*
         * Evitiamo che l'utente imposti
         * nuovamente la stessa password.
         */
        if (
            passwordService.matches(
                password,
                user.getPassword()
            )
        ) {

            throw new ServiceException(
                "La nuova password deve essere diversa "
                + "da quella attuale"
            );
        }

        /*
         * Codifica della nuova password.
         */
        String hashedPassword =
            passwordService.encode(
                password
            );

        user.setPassword(
            hashedPassword
        );

        /*
         * Salvataggio dell'utente aggiornato.
         */
        userRepo.save(
            user
        );
    }

    /**
     * Effettua il login HairLab.
     *
     * Verifica:
     *
     * - email;
     * - password.
     *
     * Se le credenziali sono corrette,
     * genera un JWT.
     *
     * @param dto credenziali di login
     * @return DTO contenente il JWT
     * @throws ServiceException se le credenziali sono errate
     */
    public LoginResponseDto login(
        LoginRequestDto dto
    )
    throws ServiceException {

        /*
         * Cerchiamo l'utente tramite email.
         */
        User user =
            userRepo.findByEmail(
                dto.getEmail()
            );

        /*
         * Utilizziamo un messaggio generico.
         *
         * In questo modo non riveliamo
         * se l'email esiste oppure no.
         */
        if (user == null) {

            throw new ServiceException(
                "Invalid credentials"
            );
        }

        /*
         * Confrontiamo:
         *
         * password ricevuta
         *
         * con
         *
         * password hashata nel database.
         */
        if (
            !passwordService.matches(
                dto.getPassword(),
                user.getPassword()
            )
        ) {

            throw new ServiceException(
                "Invalid credentials"
            );
        }

        /*
         * Generazione del JWT.
         */
        String token =
            jwtService.generateToken(
                user
            );

        /*
         * Preparazione della risposta.
         */
        LoginResponseDto response =
            new LoginResponseDto();

        response.setToken(
            token
        );

        return response;
    }

    /**
     * Inserisce un nuovo utente.
     *
     * Regola di sicurezza:
     *
     * UTENTE NON AUTENTICATO
     * ->
     * può creare solamente un USER.
     *
     * ADMIN AUTENTICATO
     * ->
     * può scegliere il ruolo del nuovo utente.
     *
     * Questo impedisce a un client anonimo
     * di inviare:
     *
     * role = ADMIN
     *
     * e autoassegnarsi privilegi amministrativi.
     *
     * @param dto dati del nuovo utente
     * @return utente creato
     * @throws ServiceException in caso di dati non validi
     */
    public UserDto insert(
        UserDto dto
    )
    throws ServiceException {

        /*
         * ========================================================
         * VALIDAZIONE EMAIL
         * ========================================================
         */

        if (
            dto.getEmail() == null ||
            dto.getEmail().isBlank()
        ) {

            throw new ServiceException(
                "Email obbligatoria"
            );
        }

        /*
         * ========================================================
         * VALIDAZIONE PASSWORD
         * ========================================================
         */

        if (
            dto.getPassword() == null ||
            dto.getPassword().isBlank()
        ) {

            throw new ServiceException(
                "Password obbligatoria"
            );
        }

        /*
         * ========================================================
         * CONTROLLO EMAIL DUPLICATA
         * ========================================================
         */

        if (
            userRepo.findByEmail(
                dto.getEmail()
            ) != null
        ) {

            throw new ServiceException(
                "Esiste già un utente "
                + "con questa email"
            );
        }

        /*
         * Da questo punto in poi
         * possono verificarsi eccezioni tecniche:
         *
         * - errori Mapper;
         * - errori Repository;
         * - errori database.
         *
         * Le convertiamo in ServiceException.
         */
        try {

            /*
             * ====================================================
             * DTO -> ENTITY
             * ====================================================
             */

            User user =
                userMapper.toEntity(
                    dto
                );

            /*
             * ====================================================
             * RUOLO PREDEFINITO
             * ====================================================
             *
             * Per sicurezza il ruolo iniziale
             * è sempre USER.
             */
            Role roleToAssign =
                Role.USER;

            /*
             * Recuperiamo l'eventuale autenticazione
             * presente nel SecurityContext.
             *
             * Anche se POST /users è pubblico,
             * JwtAuthenticationFilter analizza comunque
             * un JWT quando viene inviato.
             */
            Authentication authentication =
                SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            /*
             * Controlliamo se chi effettua
             * la richiesta possiede ROLE_ADMIN.
             */
            boolean callerIsAdmin =
                authentication != null &&

                authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(
                        authority ->
                            "ROLE_ADMIN".equals(
                                authority.getAuthority()
                            )
                    );

            /*
             * Solamente un ADMIN autenticato
             * può specificare un ruolo diverso.
             */
            if (
                callerIsAdmin &&
                dto.getRole() != null
            ) {

                roleToAssign =
                    dto.getRole();
            }

            /*
             * Assegniamo il ruolo definitivo.
             *
             * Caso utente anonimo:
             *
             * dto.role = ADMIN
             *
             * viene comunque trasformato in:
             *
             * USER.
             */
            user.setRole(
                roleToAssign
            );

            /*
             * ====================================================
             * PASSWORD
             * ====================================================
             */

            String hashedPassword =
                passwordService.encode(
                    user.getPassword()
                );

            user.setPassword(
                hashedPassword
            );

            /*
             * ====================================================
             * SALVATAGGIO
             * ====================================================
             */

            User saved =
                userRepo.save(
                    user
                );

            /*
             * Entity -> DTO.
             */
            return userMapper.toDto(
                saved
            );

        }
        catch (Exception e) {

            /*
             * Nella versione precedente avevamo:
             *
             * catch (ServiceException e)
             *
             * ma all'interno del try non veniva lanciata
             * nessuna ServiceException checked.
             *
             * Java quindi generava:
             *
             * "exception ServiceException is never thrown
             * in body of corresponding try statement"
             *
             * Ora intercettiamo solamente
             * eventuali eccezioni tecniche reali.
             */

            String message =
                e.getMessage();

            if (
                message == null ||
                message.isBlank()
            ) {

                message =
                    "Errore durante la creazione dell'utente";
            }

            throw new ServiceException(
                message
            );
        }
    }
}
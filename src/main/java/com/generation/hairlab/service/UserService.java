package com.generation.hairlab.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.LoginRequestDto;
import com.generation.hairlab.dto.LoginResponseDto;
import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.mapper.UserMapper;
import com.generation.hairlab.model.User;
import com.generation.hairlab.repository.UserRepository;
import com.generation.hairlab.security.JwtService;
import com.generation.hairlab.utility.PasswordService;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione degli utenti.
 *
 * Gestisce:
 * - ricerca dell'utente tramite email;
 * - registrazione di nuovi utenti;
 * - hashing delle password;
 * - cambio password;
 * - autenticazione;
 * - generazione del token JWT.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repository utilizzato per accedere ai dati degli utenti.
     */
    private final UserRepository userRepo;

    /**
     * Service utilizzato per codificare e verificare le password.
     */
    private final PasswordService passwordService;

    /**
     * Service utilizzato per generare i token JWT.
     */
    private final JwtService jwtService;

    /**
     * Mapper utilizzato per convertire User <-> UserDto.
     */
    private final UserMapper userMapper;


    /**
     * Cerca un utente tramite email.
     *
     * @param email email dell'utente
     * @return utente trovato oppure null se non esiste
     */
    public User findByEmail(String email) {

        return userRepo.findByEmail(email);
    }


    /**
     * Modifica la password dell'utente attualmente autenticato.
     *
     * Recupera l'email dell'utente dal SecurityContext,
     * verifica che la nuova password non corrisponda a quella
     * già salvata e successivamente salva la nuova password
     * dopo averla codificata.
     *
     * @param password nuova password in chiaro
     * @throws ServiceException se l'utente non viene trovato
     *                          oppure se la nuova password
     *                          coincide con quella precedente
     */
    public void changePassword(String password) throws ServiceException {

        /*
         * Recupera l'email dell'utente autenticato.
         *
         * Nel JWT abbiamo utilizzato l'email come subject,
         * quindi getName() restituisce l'email.
         */
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email);

        /*
         * Verifica che l'utente esista.
         */
        if (user == null) {
             throw new ServiceException(
                    "User with email "
                    + email
                    + " not found"
            );
        }

        /*
         * Verifica se la nuova password corrisponde
         * già alla password salvata.
         *
         * NON bisogna fare:
         *
         * encode(password).equals(user.getPassword())
         *
         * perché algoritmi come BCrypt generano hash differenti
         * anche partendo dalla stessa password.
         *
         * Per confrontare password in chiaro e hash
         * bisogna utilizzare matches().
         */
        if (
            passwordService.matches(password, user.getPassword())
        ) {

            throw new ServiceException(
                    "La nuova password deve essere diversa "
                    + "da quella attuale"
            );
        }

        /*
         * Codifica la nuova password.
         */
        String hashedPassword =
                passwordService.encode(password);

        /*
         * Salva solamente l'hash nel database.
         */
        user.setPassword(hashedPassword);

        userRepo.save(user);
    }


    /**
     * Effettua il login dell'utente.
     *
     * Cerca l'utente tramite email e confronta
     * la password ricevuta in chiaro con l'hash
     * memorizzato nel database.
     *
     * Se le credenziali sono corrette viene generato
     * un token JWT.
     *
     * @param dto dati necessari per il login
     * @return LoginResponseDto contenente il token JWT
     * @throws ServiceException se le credenziali non sono valide
     */
    public LoginResponseDto login(LoginRequestDto dto)
            throws ServiceException {

        /*
         * Cerca l'utente tramite email.
         */
        User user =
                userRepo.findByEmail(
                        dto.getEmail()
                );

        /*
         * Email non trovata.
         *
         * Manteniamo un messaggio generico per non rivelare
         * se l'email esiste oppure no.
         */
        if (user == null) {

            throw new ServiceException(
                    "Invalid credentials"
            );
        }

        /*
         * Confronta:
         *
         * password ricevuta in chiaro
         *
         * con
         *
         * password hashata presente nel database.
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
         * Genera il token JWT.
         */
        String token =
                jwtService.generateToken(user);

        /*
         * Prepara la risposta da restituire al client.
         */
        LoginResponseDto response =
                new LoginResponseDto();

        response.setToken(token);

        return response;
    }


    /**
     * Inserisce un nuovo utente nel database.
     *
     * La password ricevuta dal DTO viene obbligatoriamente
     * codificata prima del salvataggio.
     *
     * In questo modo nel database non viene mai salvata
     * la password in chiaro.
     *
     * @param dto dati del nuovo utente
     * @return DTO dell'utente salvato
     * @throws ServiceException in caso di errore
     */
    public UserDto insert(UserDto dto)
            throws ServiceException {

        try {

            /*
             * Converte il DTO nella Entity User.
             */
            User user =
                    userMapper.toEntity(dto);

            /*
             * Codifica la password prima del salvataggio.
             *
             * Esempio:
             *
             * password123
             *
             * diventa qualcosa simile a:
             *
             * $2a$10$.....................
             */
            String hashedPassword =
                    passwordService.encode(
                            user.getPassword()
                    );

            /*
             * Sostituisce la password in chiaro
             * con quella hashata.
             */
            user.setPassword(
                    hashedPassword
            );

            /*
             * Salva l'utente.
             *
             * Nel database verrà quindi memorizzato
             * solamente l'hash.
             */
            userRepo.save(user);

            /*
             * Converte l'utente salvato nel DTO.
             */
            return userMapper.toDto(user);

        }
        catch (Exception e) {

            throw new ServiceException(
                    e.getMessage()
            );
        }
    }
}
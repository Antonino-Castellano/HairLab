package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.FaceProfileDto;
import com.generation.hairlab.service.FaceProfileService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST per FaceProfile.
 */
@RestController
@RequestMapping("/hairlab/api/face-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FaceProfileController {

    private final FaceProfileService faceProfileService;

    /**
     * GET tutti.
     */
    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(
            faceProfileService.findAll()
        );
    }

    /**
     * GET per ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Integer id) {

        try {

            return ResponseEntity.ok(
                faceProfileService.findById(id)
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot find face profile"
                    )
                );
        }
    }

    /**
     * GET profilo tramite cliente.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomerId(
            @PathVariable Integer customerId) {

        try {

            return ResponseEntity.ok(
                faceProfileService
                    .findByCustomerId(
                        customerId
                    )
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot find customer face profile"
                    )
                );
        }
    }

    /**
     * POST.
     */
    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody FaceProfileDto dto) {

        try {

            return ResponseEntity.ok(
                faceProfileService.insert(dto)
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot save face profile"
                    )
                );
        }
    }

    /**
     * PUT.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody FaceProfileDto dto) {

        try {

            return ResponseEntity.ok(
                faceProfileService.update(
                    id,
                    dto
                )
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot update face profile"
                    )
                );
        }
    }

    /**
     * DELETE.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id) {

        try {

            faceProfileService.delete(id);

            return ResponseEntity
                .noContent()
                .build();

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot delete face profile"
                    )
                );
        }
    }
}
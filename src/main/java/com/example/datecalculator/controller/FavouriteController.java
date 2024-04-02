package com.example.datecalculator.controller;

import com.example.datecalculator.model.Favourite;
import com.example.datecalculator.service.FavouriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FavouriteController {
    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping("/getFavouriteById/{id}")
    public ResponseEntity<Favourite> getFavouriteById(@PathVariable(name = "id") Long id) {
        Favourite favourite = favouriteService.findById(id);
        return favourite != null ? ResponseEntity.ok(favourite) : ResponseEntity.notFound().build();
    }


    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> createFavourite(@RequestBody Favourite favourite) {
        Favourite createdFavourite = favouriteService.addFavourite(favourite);
        return ResponseEntity.ok(createdFavourite);
    }

    @DeleteMapping("/deleteFavourite/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable(name = "id") Long id) {
        boolean isDeleted = favouriteService.deleteFavourite(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateFavourite/{id}")
    public ResponseEntity<Favourite> updateFavourite(@RequestBody Favourite favourite, @PathVariable(name = "id") Long id) {
        Favourite updatedFavourite = favouriteService.updateFavourite(id, favourite.getDate());
        return updatedFavourite != null ? ResponseEntity.ok(updatedFavourite) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllFavourites")
    public List<Favourite> getALL() {
        return favouriteService.getAllFavourites();
    }
}

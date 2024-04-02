package com.example.datecalculator.service;

import com.example.datecalculator.model.Favourite;
import org.springframework.stereotype.Service;
import com.example.datecalculator.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public Favourite addFavourite(Favourite favourite) {
        favourite.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        favourite.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return favouriteRepository.save(favourite);

    }

    public Favourite findById(Long id) {
        return favouriteRepository.searchById(id);
    }

    public Favourite updateFavourite(Long id, Timestamp newDate) {
        Favourite favourite = favouriteRepository.findById(id).orElse(null);
        if (favourite != null) {
            favourite.setDate(newDate);
            favourite.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            favouriteRepository.save(favourite);
        }
        return favourite;
    }

    public boolean deleteFavourite(Long id) {
        Favourite favourite = favouriteRepository.findById(id).orElse(null);
        if (favourite != null) {
            favouriteRepository.delete(favourite);
            return true;
        }
        return false;
    }

    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

}

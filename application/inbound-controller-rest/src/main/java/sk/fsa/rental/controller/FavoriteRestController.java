package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.FavoriteMapper;
import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.FavoriteFacade;
import sk.fsa.rental.rest.api.FavoriteApi;
import sk.fsa.rental.rest.dto.FavoriteResponseDto;
import sk.fsa.rental.security.CurrentUserDetailService;

import java.util.List;

@RestController
public class FavoriteRestController implements FavoriteApi {

    private final FavoriteFacade favoriteFacade;
    private final FavoriteMapper favoriteMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public FavoriteRestController(FavoriteFacade favoriteFacade,
                                   FavoriteMapper favoriteMapper,
                                   CurrentUserDetailService currentUserDetailService) {
        this.favoriteFacade = favoriteFacade;
        this.favoriteMapper = favoriteMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<FavoriteResponseDto>> getMyFavorites() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(favoriteFacade.getByUser(currentUser.getId()).stream()
                .map(favoriteMapper::toDto)
                .toList());
    }

    @Override
    @Transactional
    public ResponseEntity<FavoriteResponseDto> addFavorite(Long listingId) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Favorite favorite = favoriteFacade.add(listingId, currentUser);
        return new ResponseEntity<>(favoriteMapper.toDto(favorite), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeFavorite(Long listingId) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        favoriteFacade.remove(listingId, currentUser);
        return ResponseEntity.noContent().build();
    }
}

package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.ViewingRequestMapper;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.facade.ViewingRequestFacade;
import sk.fsa.rental.rest.api.ViewingRequestApi;
import sk.fsa.rental.rest.dto.CreateViewingRequestDto;
import sk.fsa.rental.rest.dto.ViewingRequestResponseDto;
import sk.fsa.rental.security.CurrentUserDetailService;

import java.util.List;

@RestController
public class ViewingRequestRestController implements ViewingRequestApi {

    private final ViewingRequestFacade viewingRequestFacade;
    private final ViewingRequestMapper viewingRequestMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public ViewingRequestRestController(ViewingRequestFacade viewingRequestFacade,
                                         ViewingRequestMapper viewingRequestMapper,
                                         CurrentUserDetailService currentUserDetailService) {
        this.viewingRequestFacade = viewingRequestFacade;
        this.viewingRequestMapper = viewingRequestMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional
    public ResponseEntity<ViewingRequestResponseDto> createViewingRequest(CreateViewingRequestDto dto) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        ViewingRequest viewingRequest = viewingRequestMapper.toDomain(dto);
        ViewingRequest created = viewingRequestFacade.createRequest(viewingRequest, dto.getListingId(), currentUser);
        return new ResponseEntity<>(viewingRequestMapper.toDto(created), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ViewingRequestResponseDto>> getMyViewingRequests() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(viewingRequestFacade.listByRequester(currentUser.getId()).stream()
                .map(viewingRequestMapper::toDto)
                .toList());
    }

    @Override
    @Transactional
    public ResponseEntity<List<ViewingRequestResponseDto>> getOwnerViewingRequests() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(viewingRequestFacade.listByOwner(currentUser.getId()).stream()
                .map(viewingRequestMapper::toDto)
                .toList());
    }

    @Override
    @Transactional
    public ResponseEntity<ViewingRequestResponseDto> approveViewingRequest(Long id) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(viewingRequestMapper.toDto(viewingRequestFacade.approve(id, currentUser)));
    }

    @Override
    @Transactional
    public ResponseEntity<ViewingRequestResponseDto> rejectViewingRequest(Long id) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(viewingRequestMapper.toDto(viewingRequestFacade.reject(id, currentUser)));
    }

    @Override
    @Transactional
    public ResponseEntity<ViewingRequestResponseDto> cancelViewingRequest(Long id) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(viewingRequestMapper.toDto(viewingRequestFacade.cancel(id, currentUser)));
    }
}

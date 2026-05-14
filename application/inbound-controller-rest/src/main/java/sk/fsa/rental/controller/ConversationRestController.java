package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.ConversationMapper;
import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ConversationFacade;
import sk.fsa.rental.rest.api.ConversationApi;
import sk.fsa.rental.rest.dto.ConversationResponseDto;
import sk.fsa.rental.rest.dto.CreateConversationRequestDto;
import sk.fsa.rental.rest.dto.MessageResponseDto;
import sk.fsa.rental.rest.dto.SendMessageRequestDto;
import sk.fsa.rental.security.CurrentUserDetailService;

import java.util.List;

@RestController
public class ConversationRestController implements ConversationApi {

    private final ConversationFacade conversationFacade;
    private final ConversationMapper conversationMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public ConversationRestController(ConversationFacade conversationFacade,
                                      ConversationMapper conversationMapper,
                                      CurrentUserDetailService currentUserDetailService) {
        this.conversationFacade = conversationFacade;
        this.conversationMapper = conversationMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional
    public ResponseEntity<List<ConversationResponseDto>> getMyConversations() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(conversationFacade.listForUser(currentUser).stream()
                .map(conversation -> conversationMapper.toDto(conversation, currentUser))
                .toList());
    }

    @Override
    @Transactional
    public ResponseEntity<ConversationResponseDto> openConversation(CreateConversationRequestDto dto) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Conversation conversation = conversationFacade.open(
                dto.getListingId(), currentUser, dto.getInitialMessage());
        return ResponseEntity.ok(conversationMapper.toDto(conversation, currentUser));
    }

    @Override
    @Transactional
    public ResponseEntity<ConversationResponseDto> getConversation(Long conversationId) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Conversation conversation = conversationFacade.getConversation(conversationId, currentUser);
        return ResponseEntity.ok(conversationMapper.toDto(conversation, currentUser));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponseDto> sendConversationMessage(Long conversationId, SendMessageRequestDto dto) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Message message = conversationFacade.sendMessage(conversationId, currentUser, dto.getText());
        return new ResponseEntity<>(conversationMapper.toDto(message, currentUser), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<ConversationResponseDto> markConversationRead(Long conversationId) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Conversation conversation = conversationFacade.markRead(conversationId, currentUser);
        return ResponseEntity.ok(conversationMapper.toDto(conversation, currentUser));
    }
}

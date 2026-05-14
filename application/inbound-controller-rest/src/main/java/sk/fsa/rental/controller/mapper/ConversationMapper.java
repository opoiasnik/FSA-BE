package sk.fsa.rental.controller.mapper;

import org.springframework.stereotype.Component;
import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.rest.dto.ConversationResponseDto;
import sk.fsa.rental.rest.dto.MessageResponseDto;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Date;

@Component
public class ConversationMapper {

    private final ListingMapper listingMapper;
    private final UserMapper userMapper;

    public ConversationMapper(ListingMapper listingMapper, UserMapper userMapper) {
        this.listingMapper = listingMapper;
        this.userMapper = userMapper;
    }

    public ConversationResponseDto toDto(Conversation conversation, User currentUser) {
        return new ConversationResponseDto()
                .id(conversation.getId())
                .listing(listingMapper.toSummary(conversation.getListing()))
                .peer(userMapper.toDto(conversation.peerFor(currentUser)))
                .createdAt(toOffsetDateTime(conversation.getCreatedAt()))
                .updatedAt(toOffsetDateTime(conversation.getUpdatedAt()))
                .unreadCount(conversation.unreadCountFor(currentUser))
                .preview(lastMessageText(conversation))
                .messages(conversation.getMessages().stream()
                        .map(message -> toDto(message, currentUser))
                        .toList());
    }

    public MessageResponseDto toDto(Message message, User currentUser) {
        return new MessageResponseDto()
                .id(message.getId())
                .sender(userMapper.toDto(message.getSender()))
                .text(message.getText())
                .sentAt(toOffsetDateTime(message.getSentAt()))
                .readAt(toOffsetDateTime(message.getReadAt()))
                .ownMessage(isOwnMessage(message, currentUser));
    }

    private OffsetDateTime toOffsetDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.UTC);
    }

    private String lastMessageText(Conversation conversation) {
        return conversation.getMessages().stream()
                .max(Comparator.comparing(Message::getSentAt, Comparator.nullsFirst(Date::compareTo)))
                .map(Message::getText)
                .orElse(null);
    }

    private boolean isOwnMessage(Message message, User currentUser) {
        return message.getSender() != null
                && currentUser != null
                && message.getSender().getId() != null
                && message.getSender().getId().equals(currentUser.getId());
    }
}

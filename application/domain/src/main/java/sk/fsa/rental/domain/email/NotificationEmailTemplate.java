package sk.fsa.rental.domain.email;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;

import java.time.Year;

public class NotificationEmailTemplate {

    public EmailMessage messageReceived(Conversation conversation, Message message, User sender, User recipient) {
        String listingTitle = escape(conversation.getListing().getTitle());
        String senderName = escape(fullName(sender));
        return new EmailMessage(
                "New RentArea message",
                layout(
                        "New message",
                        "You received a new message about " + listingTitle + ".",
                        senderName + " wrote:",
                        escape(message.getText()),
                        recipient.getEmail()
                )
        );
    }

    public EmailMessage viewingStatusChanged(ViewingRequest viewingRequest) {
        String status = viewingRequest.getStatus().name().toLowerCase();
        String listingTitle = escape(viewingRequest.getListing().getTitle());
        return new EmailMessage(
                "Your RentArea viewing request was " + status,
                layout(
                        "Viewing " + status,
                        "Your viewing request for " + listingTitle + " was " + status + ".",
                        "Listing",
                        listingTitle,
                        viewingRequest.getRequester().getEmail()
                )
        );
    }

    public EmailMessage viewingRequestCreated(ViewingRequest viewingRequest) {
        String listingTitle = escape(viewingRequest.getListing().getTitle());
        String requesterName = escape(fullName(viewingRequest.getRequester()));
        return new EmailMessage(
                "New RentArea viewing request",
                layout(
                        "New viewing request",
                        requesterName + " requested a viewing for " + listingTitle + ".",
                        "Requested date",
                        viewingRequest.getRequestedDate().toString(),
                        viewingRequest.getOwner().getEmail()
                )
        );
    }

    public EmailMessage viewingCancelled(ViewingRequest viewingRequest) {
        String listingTitle = escape(viewingRequest.getListing().getTitle());
        String requesterName = escape(fullName(viewingRequest.getRequester()));
        return new EmailMessage(
                "RentArea viewing request cancelled",
                layout(
                        "Viewing request cancelled",
                        requesterName + " cancelled a viewing request for " + listingTitle + ".",
                        "Requested date",
                        viewingRequest.getRequestedDate().toString(),
                        viewingRequest.getOwner().getEmail()
                )
        );
    }

    private String layout(String title, String lead, String label, String value, String recipientEmail) {
        int year = Year.now().getValue();
        String safeRecipient = escape(recipientEmail);
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>RentArea Notification</title>
                </head>
                <body style="margin:0;padding:0;background:#fff5f7;font-family:Arial,Helvetica,sans-serif;color:#101828;">
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background:#fff5f7;padding:28px 16px;">
                    <tr>
                      <td align="center">
                        <table role="presentation" width="620" cellpadding="0" cellspacing="0" style="max-width:620px;width:100%%;">
                          <tr><td style="padding:20px 8px;color:#ff3366;font-size:22px;font-weight:700;">RentArea</td></tr>
                          <tr>
                            <td style="background:#ffffff;border:1px solid #f2d7df;border-radius:16px;overflow:hidden;box-shadow:0 18px 45px rgba(16,24,40,0.08);">
                              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td style="background:#ff3366;padding:28px 32px;color:#ffffff;">
                                    <div style="font-size:13px;text-transform:uppercase;letter-spacing:1.4px;font-weight:700;">Notification</div>
                                    <h1 style="margin:8px 0 0;font-size:28px;line-height:34px;">%s</h1>
                                  </td>
                                </tr>
                                <tr>
                                  <td style="padding:32px;">
                                    <p style="margin:0 0 18px;font-size:16px;line-height:24px;">%s</p>
                                    <p style="margin:0 0 8px;font-size:13px;line-height:18px;color:#667085;text-transform:uppercase;letter-spacing:1px;font-weight:700;">%s</p>
                                    <div style="padding:18px;border-radius:12px;background:#fff0f4;font-size:16px;line-height:24px;">%s</div>
                                  </td>
                                </tr>
                                <tr>
                                  <td style="background:#f8fafc;padding:22px 32px;color:#667085;font-size:13px;line-height:20px;">
                                    This email was sent to <a href="mailto:%s" style="color:#ff3366;text-decoration:underline;">%s</a>.
                                    <br>Copyright %d RentArea. All rights reserved.
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(escape(title), escape(lead), escape(label), value, safeRecipient, safeRecipient, year);
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private String fullName(User user) {
        return (nullToEmpty(user.getName()) + " " + nullToEmpty(user.getSurname())).trim();
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

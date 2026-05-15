package sk.fsa.rental.domain.email;

import sk.fsa.rental.domain.User;

import java.time.Year;

public class EmailVerificationTemplate {

    public EmailMessage create(User user, String code) {
        return new EmailMessage(
                "RentArea email verification",
                buildBody(user, code)
        );
    }

    private String buildBody(User user, String code) {
        String name = escape(user.getName());
        String email = escape(user.getEmail());
        String safeCode = escape(code);
        int year = Year.now().getValue();

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>RentArea Email Verification</title>
                </head>
                <body style="margin:0;padding:0;background:#fff5f7;font-family:Arial,Helvetica,sans-serif;color:#101828;">
                  <div style="display:none;overflow:hidden;line-height:1px;opacity:0;max-height:0;max-width:0;">
                    Your RentArea verification code.
                  </div>
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background:#fff5f7;padding:28px 16px;">
                    <tr>
                      <td align="center">
                        <table role="presentation" width="620" cellpadding="0" cellspacing="0" style="max-width:620px;width:100%%;">
                          <tr>
                            <td style="padding:20px 8px;color:#ff3366;font-size:22px;font-weight:700;">
                              RentArea
                            </td>
                          </tr>
                          <tr>
                            <td style="background:#ffffff;border:1px solid #f2d7df;border-radius:16px;overflow:hidden;box-shadow:0 18px 45px rgba(16,24,40,0.08);">
                              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td style="background:#ff3366;padding:28px 32px;color:#ffffff;">
                                    <div style="font-size:13px;text-transform:uppercase;letter-spacing:1.4px;font-weight:700;">Verification</div>
                                    <h1 style="margin:8px 0 0;font-size:28px;line-height:34px;">Confirm your email address</h1>
                                  </td>
                                </tr>
                                <tr>
                                  <td style="padding:32px;">
                                    <p style="margin:0 0 18px;font-size:16px;line-height:24px;">Hello %s,</p>
                                    <p style="margin:0 0 18px;font-size:16px;line-height:24px;">Use this one-time code to verify your RentArea account email address.</p>
                                    <div style="margin:24px 0;padding:22px;border-radius:12px;background:#fff0f4;text-align:center;color:#101828;font-size:34px;line-height:42px;font-weight:700;letter-spacing:7px;">
                                      %s
                                    </div>
                                    <p style="margin:0;font-size:15px;line-height:23px;color:#475467;">This code expires in 15 minutes. Do not share it with anyone.</p>
                                    <p style="margin:20px 0 0;font-size:15px;line-height:23px;color:#475467;">If you did not request this code, you can safely ignore this email.</p>
                                  </td>
                                </tr>
                                <tr>
                                  <td style="background:#f8fafc;padding:22px 32px;color:#667085;font-size:13px;line-height:20px;">
                                    This email was sent to <a href="mailto:%s" style="color:#ff3366;text-decoration:underline;">%s</a>.
                                    <br>
                                    Copyright %d RentArea. All rights reserved.
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
                """.formatted(name, safeCode, email, email, year);
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

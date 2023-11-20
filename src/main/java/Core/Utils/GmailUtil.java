package Core.Utils;

import Core.Support.General.PropertyBuilder;

import javax.annotation.Nullable;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GmailUtil {
    private final static Logger LOGGER = Logger.getLogger(GmailUtil.class.getCanonicalName());
    private String mail;
    private String password;
    private Properties props;
    private Store store;
    private Message[] messages;

    public GmailUtil(String mail, String password) {
        this.mail = mail;
        this.password = password;
        props = PropertyBuilder.getGmailConfig();
        props.put("mail.imap.ssl.enable", "true"); // required for Gmail
        props.put("mail.imap.auth.mechanisms", "XOAUTH2");
        this.connect();
    }

    private void connect() {
        try {
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] Establishing connection to [%s] .....", this.mail));
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", this.mail, this.password);
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] Connection to [%s] us established successfully .....", this.mail));
        } catch (MessagingException e) {
            LOGGER.log(Level.WARNING, "--> [WARNING] Fail to get message .....");
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    public ArrayList<String> getMailSubjectsInFolder(@Nullable String folder) throws MessagingException {
        try {
            ArrayList<String> mailsSubject = new ArrayList<>();
            String targetFolder = folder == null ? "inbox" : folder;
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] Opening folder [%s] .....", targetFolder));
            Folder mailFolder = store.getFolder("inbox");
            mailFolder.open(Folder.READ_WRITE);
            messages = mailFolder.getMessages();
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] There are [%s] messages in folder [%s] .....", messages.length, targetFolder));
            for (Message message : messages) {
                mailsSubject.add(message.getSubject());
                System.out.println("--> Mail Subject: " + message.getSubject());
            }
            return mailsSubject;
        } catch (MessagingException e) {
            LOGGER.log(Level.WARNING, "--> [WARNING] Fail to get message .....");
            return null;
        } finally {
            store.close();
        }
    }

    public String getMessageBySubject(String subject) throws IOException, MessagingException {
        try {
            ArrayList<String> mailsSubject = new ArrayList<>();
            String targetFolder = "inbox";
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] Opening folder [%s] .....", targetFolder));
            Folder mailFolder = store.getFolder("inbox");
            mailFolder.open(Folder.READ_WRITE);

            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains(subject)) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };
            messages = mailFolder.search(searchCondition);
            LOGGER.log(Level.INFO, String.format("--> [Mail-step] There are [%s] messages match with search criteria in folder [%s] .....", messages.length, targetFolder));
            return getTextFromMessage(messages[0]);
        } catch (MessagingException e) {
            LOGGER.log(Level.WARNING, "--> [WARNING] Fail to get message .....");
            return null;
        } finally {
            store.close();
        }
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public void deleteAllInboxMessagesAndCloseConnection() throws MessagingException {
        this.connect();
        Flags deleted = new Flags(Flags.Flag.DELETED);
        Folder mailFolder = store.getFolder("inbox");
        mailFolder.open(Folder.READ_WRITE);
        messages = mailFolder.getMessages();
        mailFolder.setFlags(messages, deleted, true);
        mailFolder.expunge(); // or folder.close(true);
        store.close();
    }
}

/**
 *
 */
package org.phoebus.applications.email;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.phoebus.email.EmailPreferences;
import org.phoebus.framework.spi.AppInstance;
import org.phoebus.framework.spi.AppResourceDescriptor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kunal Shroff
 *
 */
public class EmailApp implements AppResourceDescriptor {

    private static final Logger log = Logger.getLogger(AppResourceDescriptor.class.getName());
    public static final String NAME = "email";
    public static final String DISPLAY_NAME = "Send Email";

    private Session session;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    /**
     * Create the {@link Session} at startup
     */
    @Override
    public void start() {
        final Properties props = new Properties();
        props.put("mail.smtp.host", EmailPreferences.mailhost);
        props.put("mail.smtp.port", EmailPreferences.mailport);

        final String username = EmailPreferences.username;
        final String password = EmailPreferences.password;

        if (!username.isEmpty() && !password.isEmpty()) {
            PasswordAuthentication auth = new PasswordAuthentication(username, password);
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return auth;
                }
            });
        } else {
            session = Session.getDefaultInstance(props);
        }
    }

    @Override
    public AppInstance create() {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("ui/SimpleCreate.fxml"));
            Scene scene = new Scene(root, 600, 800);

            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            log.log(Level.WARNING, "Failed to create email dialog", e);
        }
        return null;
    }

    /**
     * Handle resources like mailto:shroffk@....
     */
    @Override
    public AppInstance create(URI resource) {
        return create();
    }

    /**
     *
     * @return {@link Session} connection factory needed to create and send emails.
     */
    public Session getSession() {
        return session;
    }
}

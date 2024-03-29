package com.michael200kg.tests.ldap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapException;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AccountState;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;
import org.ldaptive.auth.ext.ActiveDirectoryAuthenticationResponseHandler;
import org.ldaptive.auth.ext.PasswordPolicyAuthenticationRequestHandler;
import org.ldaptive.auth.ext.PasswordPolicyAuthenticationResponseHandler;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SaslConfig;
import org.ldaptive.ssl.AllowAnyHostnameVerifier;
import org.ldaptive.ssl.AllowAnyTrustManager;
import org.ldaptive.ssl.KeyStoreCredentialConfig;
import org.ldaptive.ssl.SslConfig;

import static java.util.Objects.isNull;

/**
 * @author Mikhail_Vershkov
 */

public class LdapKeytabTest {

    private static String searchUserName = "Mikhail_Vershkov@epam.com";
    private static final String DN = "userprincipalname";
    public static void main(String[] args) throws LdapException {

        PooledConnectionFactory connectionFactory = PooledConnectionFactory.builder()
                .config(ConnectionConfig.builder()
                        .url("ldaps://evhubudsa0047.epam.com:3269")
                        .sslConfig(SslConfig.builder()
                                .hostnameVerifier(new AllowAnyHostnameVerifier())
                                .trustManagers(new AllowAnyTrustManager())
                                .build())
                        .responseTimeout(Duration.ofSeconds(1000L))
                        .connectionInitializers(BindConnectionInitializer.builder()
                                .saslConfig(SaslConfig.builder()
                                        .qualityOfProtection(QualityOfProtection.AUTH)
                                        .mechanism(Mechanism.GSSAPI)
                                        .mutualAuthentication(true)
                                        .property("org.ldaptive.sasl.gssapi.jaas.debug", "false")
                                        .property("org.ldaptive.sasl.gssapi.jaas.principal", "Mikhail_Vershkov@EPAM.COM")
                                        .property("org.ldaptive.sasl.gssapi.jaas.useKeyTab", "true")
                                        .property("org.ldaptive.sasl.gssapi.jaas.keyTab", "c:\\tmp\\krb5.keytab")
                                        .build())
                                .build())
                        .build())
                .min(1)
                .max(1)
                .build();
        try {
            connectionFactory.initialize();
            System.out.println("Connected!");
            searchUser(connectionFactory);
        } finally {
            connectionFactory.close();
        }
    }
    public static void searchUser(ConnectionFactory connectionFactory) throws LdapException {
        SearchOperation search = new SearchOperation(connectionFactory, "dc=epam,dc=com");
        System.out.println("Search begins!");
        SearchResponse response = search.execute(
                new FilterTemplate("(&(objectCategory=user)(userPrincipalName={0}))", new Object[] {searchUserName}),
                DN);
        System.out.println("Entry: " + response.getEntry());
    }

    //  -Djava.security.krb5.kdc=evhubudsa0006.epam.com -Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.krb5.realm=EPAM.COM
    // 80090324: LdapErr: DSID-0C09058A, comment: AcceptSecurityContext error, data 576, v4563 
    //  -Djava.security.auth.login.config=c:\\tmp\\jaas.cfg -Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.krb5.kdc=evhubudsa0047.epam.com -Djava.security.krb5.realm=EPAM.COM
}

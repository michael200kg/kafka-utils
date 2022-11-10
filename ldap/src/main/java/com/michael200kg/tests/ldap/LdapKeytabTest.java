package com.michael200kg.tests.ldap;

import java.time.Duration;

import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapException;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SaslConfig;
import org.ldaptive.ssl.AllowAnyHostnameVerifier;
import org.ldaptive.ssl.AllowAnyTrustManager;
import org.ldaptive.ssl.SslConfig;

import static java.util.Objects.isNull;

/**
 * @author Mikhail_Vershkov
 */

public class LdapKeytabTest {

    private static String userName = "Mikhail_Vershkov@epam.com";
    private static String principal = "Mikhail_Vershkov@EPAM.COM";
    private static String pathToKeytab = "c:\\tmp\\krb5.keytab";

    public static void main(String[] args) throws LdapException {

        if(isNull(args) || args.length != 3) {
            System.out.println("Usage: java -jar ldap.jar <principal> <pathToKeytab> <searchUserName>");
        } else {
            principal = args[0];
            pathToKeytab = args[1];
            userName = args[2];
;        }

        PooledConnectionFactory connectionFactory = PooledConnectionFactory.builder()
                .config(ConnectionConfig.builder()
                        .url("ldaps://minsk.epam.com:3269")
                        //.useStartTLS(true)
                        .sslConfig(SslConfig.builder()
                                .hostnameVerifier(new AllowAnyHostnameVerifier())
                                .trustManagers(new AllowAnyTrustManager())
                                .build())
                        .responseTimeout(Duration.ofSeconds(1000L))
                        .connectTimeout(Duration.ofSeconds(1000L))
                        .connectionInitializers(BindConnectionInitializer.builder()
                                .saslConfig(SaslConfig.builder()
                                        .mechanism(Mechanism.GSSAPI)
                                        //.realm("EPAM.COM")
                                        .qualityOfProtection(QualityOfProtection.AUTH_INT)
                                        .property("org.ldaptive.sasl.gssapi.jaas.debug", "true")
                                        .property("org.ldaptive.sasl.gssapi.jaas.principal", principal)
                                        .property("org.ldaptive.sasl.gssapi.jaas.useKeyTab", "true")
                                        .property("org.ldaptive.sasl.gssapi.jaas.keyTab", pathToKeytab)
                                        .property("org.ldaptive.sasl.gssapi.jaas.storeKey", "true")
                                        .property("org.ldaptive.sasl.gssapi.jaas.useTicketCache", "true")
                                        //.property("org.ldaptive.sasl.gssapi.jaas.isInitiator", "true")
                                        .build())
                                .build())
                        .build())
                .min(3)
                .max(6)
                .build();
        connectionFactory.initialize();
        System.out.println("Connected!");
        searchUser(connectionFactory);
        connectionFactory.close();
    }
    public static void searchUser(ConnectionFactory connectionFactory) throws LdapException {
        SearchOperation search = new SearchOperation(connectionFactory, "dc=epam,dc=com");
        System.out.println("Search begins!");
        SearchResponse response = search.execute(
                new FilterTemplate("(&(objectCategory=user)(userPrincipalName={0}))", new Object[] {userName}));
        System.out.println("Entry: " + response.getEntry());
    }
}

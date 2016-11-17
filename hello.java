package test;

import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

public class Main {

    public static void main(String[] args) {

        try {
            URL url = new URL("https://www.baidu.com");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {

                }
            };

            TrustManager[] tm = { xtm };

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], tm, new SecureRandom());

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            InputStreamReader insr = new InputStreamReader(con.getInputStream());

            int respInt = insr.read();
            while( respInt != -1){
                System.out.print((char)respInt);
                respInt = insr.read();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

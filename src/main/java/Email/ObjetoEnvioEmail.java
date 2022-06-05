package Email;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ObjetoEnvioEmail {

    private String userName="userJavaLearn@gmail.com";
    private String senha="@Alan2112";
    private String listaDestinatarios="";
    private String remetente="";
    private String assuntoEmail ="" ;
    private String textoEmail="";

    public ObjetoEnvioEmail(String listaDestinatario, String nomeRemetente, String assunto, String texto){
        this.listaDestinatarios=listaDestinatario;
        this.remetente=nomeRemetente;
        this.assuntoEmail=assunto;
        this.textoEmail=texto;
    }

    public void enviarEmail(boolean enviohtml) throws MessagingException, UnsupportedEncodingException {

        Properties properties = new Properties();

        properties.put( "mail.smtp.ssl.trust","*" );
        properties.put( "mail.smtp.auth", "true" ); //Autorização
        properties.put( "mail.smtp.starttls", "true" ); //Autenticação
        properties.put( "mail.smtp.host", "smtp.gmail.com" ); //Servidor google
        properties.put( "mail.smtp.port", "465" ); //Porta servidor
        properties.put( "mail.smtp.socketFactory.port", "465" ); //Especificar porta a ser conectada, libera envio email
        properties.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" ); //Classe socket conexão SMTP

        Session session = Session.getInstance( properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,senha);
            }
        } );


        Address[] toUser = InternetAddress.parse( listaDestinatarios );
        Message message = new MimeMessage( session );
        message.setFrom(new InternetAddress( userName, remetente));// Quem está enviando
        message.setRecipients( Message.RecipientType.TO, toUser );//Email destino
        message.setSubject( assuntoEmail);//Assunto

        if(enviohtml){
            message.setContent( textoEmail,"text/html; charset=utf-8" );
        }else{
            message.setText( textoEmail );//Mensagem sem html
        }

        Transport.send( message );
    }

    public void enviarEmailComPDF(boolean enviohtml) throws Exception {


        Properties properties = new Properties();

        properties.put( "mail.smtp.ssl.trust","*" );
        properties.put( "mail.smtp.auth", "true" ); //Autorização
        properties.put( "mail.smtp.starttls", "true" ); //Autenticação
        properties.put( "mail.smtp.host", "smtp.gmail.com" ); //Servidor google
        properties.put( "mail.smtp.port", "465" ); //Porta servidor
        properties.put( "mail.smtp.socketFactory.port", "465" ); //Especificar porta a ser conectada, libera envio email
        properties.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" ); //Classe socket conexão SMTP

        Session session = Session.getInstance( properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,senha);
            }
        } );


        Address[] toUser = InternetAddress.parse( listaDestinatarios );
        Message message = new MimeMessage( session );
        message.setFrom(new InternetAddress( userName, remetente));// Quem está enviando
        message.setRecipients( Message.RecipientType.TO, toUser );//Email destino
        message.setSubject( assuntoEmail);//Assunto

        //Descrição email
        MimeBodyPart corpoEmail = new MimeBodyPart();

        if(enviohtml){
            corpoEmail.setContent( textoEmail,"text/html; charset=utf-8" );
        }else{
            corpoEmail.setText( textoEmail );//Mensagem sem html
        }

        List <FileInputStream> arquivo = new ArrayList <FileInputStream>( );
        arquivo.add( simuladorPDF());
        arquivo.add( simuladorPDF());
        arquivo.add( simuladorPDF());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart( corpoEmail );

        int index=0;

        for (FileInputStream fileInputStream: arquivo){

            //Anexo email
            MimeBodyPart anexoEmail = new MimeBodyPart();
            //Informa o simulador do arquivo
            anexoEmail.setDataHandler( new DataHandler( new ByteArrayDataSource( simuladorPDF(), "application/pdf" )));
            anexoEmail.setFileName( "anexoEmail"+index+".pdf" );

            multipart.addBodyPart( anexoEmail );

            index++;
        }


        message.setContent( multipart);

        Transport.send( message );
    }

    //Este método simula PDF ou Algum arquivo de anexo email
    private FileInputStream simuladorPDF()throws Exception{
        Document document = new Document();
        File file = new File("fileAnexo.pdf");
        file.createNewFile();
        PdfWriter.getInstance( document, new FileOutputStream( file ));
        document.open();
        document.add(new Paragraph( "conteúdo PDF anexo com Java Mail. Texto do PDF" ));
        document.close();
        return new FileInputStream(file);
    }

}

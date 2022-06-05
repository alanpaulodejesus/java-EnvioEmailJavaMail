package Email;

import org.junit.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public class testEmail {

    @Test
    public void enviarEmail() throws Exception {

        StringBuilder stringBuilderTextoEmail = new StringBuilder();

        stringBuilderTextoEmail.append( "Olá, <br/><br/>" );
        stringBuilderTextoEmail.append( "<h4>Este é um email enviado por Java</h4> <br/><br/>" );
        stringBuilderTextoEmail.append( "Clique no comando abaixo para mais informações sobre o repositório: <br/><br/>" );
        stringBuilderTextoEmail.append( "<a target=\"_blank\" href=\"https://github.com/alanpaulodejesus/EnvioEmailJavaMail\" style=\"color:#2525a7; padding:14px 25px; text-align:center; text-decoration:none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border:3px solid green;background-color:#99DA39;\"> Acessar Repositório</a><br/><br/>" );
        stringBuilderTextoEmail.append( "<span style=\"font-size:11px\">Ass: Repositório Alan Paulo de Jesus</span>" );

        ObjetoEnvioEmail envioEmail= new ObjetoEnvioEmail(
                "alanpaullo@yahoo.com.br",
                "Aprendizado Java",
                "Envio de Email",
               // "Aqui está sendo enviado um email em java\n", Alterado usando opção true
                stringBuilderTextoEmail.toString()
        );

        envioEmail.enviarEmailComPDF(true);
    }

}

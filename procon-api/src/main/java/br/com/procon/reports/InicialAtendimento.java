package br.com.procon.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Configuracao;
import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.utils.MascarasUtils;
import br.com.procon.utils.ReportsUtils;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class InicialAtendimento {

	public static ByteArrayInputStream gerar(Atendimento atendimento, Configuracao config) {
		try {
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cabeçalho
			document = ReportsUtils.setCabecalho(config, document);

			// identificação
			Paragraph identificacao = new Paragraph(
					String.format("Atendimento nº %04d", atendimento.getId()),
					ReportsUtils.intFont);
			identificacao.setAlignment(Element.ALIGN_LEFT);
			document.add(identificacao);
			for (Consumidor c : atendimento.getConsumidores()) {
				identificacao = new Paragraph("Consumidor: " + c.getDenominacao(),
						ReportsUtils.intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: " + c.getEndereco().getLogradouro() + ", "
						+ c.getEndereco().getNumero() + ", " + c.getEndereco().getComplemento()
						+ ", " + c.getEndereco().getBairro() + ", " + c.getEndereco().getMunicipio()
						+ ", " + c.getEndereco().getUf() + ", CEP "
						+ MascarasUtils.format("#####-###", c.getEndereco().getCep()),
						ReportsUtils.intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao = new Paragraph("Fone(s): " + String.join(", ", fones),
						ReportsUtils.intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			for (Fornecedor f : atendimento.getFornecedores()) {
				identificacao = new Paragraph(
						"Fornecedor: " + f.getFantasia() + "(" + f.getRazaoSocial() + ")",
						ReportsUtils.intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			document.add(ReportsUtils.espaco);

			Paragraph titulo = new Paragraph("ATENDIMENTO", ReportsUtils.intFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			document.add(ReportsUtils.espaco);

			Paragraph conteudo = new Paragraph(atendimento.getRelato(), ReportsUtils.intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conteudo);

			for (int i = 0; i < 2; i++)
				document.add(ReportsUtils.espaco);

			conteudo = new Paragraph(
					"Consumidor                                                 PROCON",
					ReportsUtils.intFont);
			conteudo.setAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);

			conteudo = new Paragraph("Atendente: " + atendimento.getAtendente().getNome(),
					ReportsUtils.minFont);
			conteudo.setAlignment(Element.ALIGN_RIGHT);
			document.add(conteudo);

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro na geração de relatórios!", e.getCause());
		}
	}

}

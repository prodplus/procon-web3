package br.com.procon.reports;

import static br.com.procon.utils.ReportsUtils.espaco;
import static br.com.procon.utils.ReportsUtils.intFont;
import static br.com.procon.utils.ReportsUtils.minFont;
import static br.com.procon.utils.ReportsUtils.negFont;
import static br.com.procon.utils.ReportsUtils.setCabecalho;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Configuracao;
import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.enums.TipoPessoa;
import br.com.procon.utils.LocalDateUtils;
import br.com.procon.utils.MascarasUtils;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class InicialProcesso {

	public static ByteArrayInputStream gerar(Processo processo, Configuracao config) {
		try {
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cabeçalho
			document = setCabecalho(config, document);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			// data
			Paragraph data = new Paragraph(String.format("Pato Branco, %02d de %s de %d",
					processo.getData().getDayOfMonth(),
					LocalDateUtils.getMesExtenso(processo.getData().getMonthValue()),
					processo.getData().getYear()), intFont);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			// identificação
			Paragraph identificacao = new Paragraph("Autos nº ", negFont);
			identificacao.add(new Chunk(processo.getAutos(), intFont));
			identificacao.setAlignment(Element.ALIGN_LEFT);
			document.add(identificacao);

			for (Consumidor c : processo.getConsumidores()) {
				identificacao = new Paragraph("Consumidor: ", negFont);
				identificacao.add(new Chunk(c.getDenominacao(), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: " + c.getEndereco().getLogradouro() + ", "
						+ c.getEndereco().getNumero() + ", " + c.getEndereco().getComplemento()
						+ ", " + c.getEndereco().getBairro() + ", " + c.getEndereco().getMunicipio()
						+ ", " + c.getEndereco().getUf() + ", CEP: "
						+ MascarasUtils.format("#####-###", c.getEndereco().getCep()), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao = new Paragraph("Fone(s): " + String.join(", ", fones), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			for (Consumidor c : processo.getRepresentantes()) {
				identificacao = new Paragraph("Consumidor: ", negFont);
				identificacao.add(new Chunk(c.getDenominacao(), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: " + c.getEndereco().getLogradouro() + ", "
						+ c.getEndereco().getNumero() + ", " + c.getEndereco().getComplemento()
						+ ", " + c.getEndereco().getBairro() + ", " + c.getEndereco().getMunicipio()
						+ ", " + c.getEndereco().getUf() + ", CEP: "
						+ MascarasUtils.format("#####-###", c.getEndereco().getCep()), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao = new Paragraph("Fone(s): " + String.join(", ", fones), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			int cont = 0;
			for (Fornecedor f : processo.getFornecedores()) {
				cont++;
				identificacao = new Paragraph(String.format("Fornecedor %02d: ", cont), negFont);
				identificacao.add(
						new Chunk(f.getRazaoSocial() != null ? f.getRazaoSocial() : f.getFantasia(),
								intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			document.add(espaco);

			Paragraph titulo = new Paragraph("HISTÓRICO DA RECLAMAÇÃO", negFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			document.add(espaco);

			StringBuilder builder1 = new StringBuilder();
			builder1.append("O(a) consumidor(a) ");
			builder1.append(processo.getConsumidores().get(0).getDenominacao());
			builder1.append(String.format(", %s %s",
					processo.getConsumidores().get(0).getTipo().equals(TipoPessoa.FISICA)
							? "inscrito(a) no CPF nº "
							: "inscrito no CNPJ nº ",
					processo.getConsumidores().get(0).getTipo().equals(TipoPessoa.FISICA)
							? MascarasUtils.format("###.###.###-##",
									processo.getConsumidores().get(0).getCadastro())
							: MascarasUtils.format("##.###.###/####-##",
									processo.getConsumidores().get(0).getCadastro())));
			if (processo.getRepresentantes() != null && !processo.getRepresentantes().isEmpty()) {
				builder1.append(", representado(a) por ");
				builder1.append(processo.getRepresentantes().get(0).getDenominacao());
				builder1.append(String.format(", %s %s",
						processo.getRepresentantes().get(0).getTipo().equals(TipoPessoa.FISICA)
								? "inscrito(a) no CPF nº "
								: "inscrito no CNPJ nº ",
						processo.getRepresentantes().get(0).getTipo().equals(TipoPessoa.FISICA)
								? MascarasUtils.format("###.###.###-##",
										processo.getRepresentantes().get(0).getCadastro())
								: MascarasUtils.format("##.###.###/####-##",
										processo.getRepresentantes().get(0).getCadastro())));
			}
			builder1.append(", formalizou reclamação em desfavor da(s) empresa(s) ");
			String[] fornString = new String[processo.getFornecedores().size()];
			for (int i = 0; i < processo.getFornecedores().size(); i++)
				fornString[i] = processo.getFornecedores().get(i).getRazaoSocial() + " (CNPJ: "
						+ MascarasUtils.format("##.###.###/####-##",
								processo.getFornecedores().get(i).getCnpj())
						+ ")";
			builder1.append(String.join(" e ", fornString));
			builder1.append(", alegando em síntese o que segue: ");

			Paragraph conteudo = new Paragraph(builder1.toString(), intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setFirstLineIndent(30f);
			document.add(conteudo);
			document.add(espaco);

			conteudo = new Paragraph(processo.getRelato(), intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conteudo);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			conteudo = new Paragraph("Consumidor                                       PROCON",
					intFont);
			conteudo.setAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);

			if (processo.getAtendente() != null) {
				conteudo = new Paragraph("Atendente: " + processo.getAtendente().getNome(),
						minFont);
				conteudo.setAlignment(Element.ALIGN_RIGHT);
				document.add(conteudo);
			}
			
			document.close();
			
			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro na geração do relatório!", e.getCause());
		}
	}

}

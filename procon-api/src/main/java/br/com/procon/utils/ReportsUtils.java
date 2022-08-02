package br.com.procon.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;

import br.com.procon.models.Configuracao;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class ReportsUtils {

	public static Font titFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
	public static Font intFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	public static Font negFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	public static Font subFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12,
			Font.UNDERLINE);
	public static Font minFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
	public static Paragraph espaco = new Paragraph(" ", intFont);

	/**
	 * Gera um cabeçalho para os documentos.
	 * 
	 * @param config
	 * @param document
	 * @return
	 * @throws DocumentException
	 */
	public static Document setCabecalho(Configuracao config, Document document)
			throws DocumentException {
		Paragraph cabecalho = new Paragraph(config.getDescricaoProcon(), titFont);
		cabecalho.setAlignment(Element.ALIGN_CENTER);
		document.add(cabecalho);
		cabecalho = new Paragraph(config.getNomeProcon(), titFont);
		cabecalho.setAlignment(Element.ALIGN_CENTER);
		document.add(cabecalho);
		cabecalho = new Paragraph(config.getEndereco(), minFont);
		cabecalho.setAlignment(Element.ALIGN_CENTER);
		document.add(cabecalho);
		cabecalho = new Paragraph(config.getEmail(), minFont);
		cabecalho.setAlignment(Element.ALIGN_CENTER);
		document.add(cabecalho);
		return document;
	}

}

package br.com.procon.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class MascarasUtils {

	/**
	 * Aplica máscara a uma string.
	 * 
	 * @param pattern
	 * @param value
	 * @return
	 */
	public static String format(String pattern, String value) {
		try {
			MaskFormatter mask;
			String tratada = value.replaceAll("[-()]*", "");
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(tratada);
		} catch (ParseException e) {
			return value;
		}
	}

	/**
	 * Aplica máscaras aos telefones.
	 * 
	 * @param value
	 * @return
	 */
	public static String foneFormat(String value) {
		try {
			if (value.charAt(2) == '9')
				return format("(##) #####-####", value);
			else
				return format("(##) ####-####", value);
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

}

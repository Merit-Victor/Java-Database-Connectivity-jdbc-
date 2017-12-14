package eg.edu.alexu.csd.oop.db.interpreter;

/**
 * @author Merit Victor
 *
 */
public class TypeFinder {

	/**
	 * @param theSubstring
	 * @return
	 */
	public String findType(String theSubstring) {
		String sub = theSubstring.replace(";", "").trim();
		boolean isString = (isString("\'", "\"", sub) && !isString("\"", "\'", sub))
				|| (isString("\"", "\'", sub) && !isString("\'", "\"", sub));
		if (isString) {
			return "varchar";
		} else {
			return "int";
		}
	}

	/**
	 * @param quotes
	 * @param otherQuotes
	 * @param sub
	 * @return
	 */
	private boolean isString(String quotes, String otherQuotes, String sub) {
		if (sub.startsWith(quotes) && sub.endsWith(quotes)) {
			String subWithoutQuotes = sub.substring(1, sub.length() - 1);
			if (!subWithoutQuotes.contains(quotes) && !subWithoutQuotes.contains(otherQuotes)) {
				return true;
			}
		}
		return false;
	}
}
	

package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
   
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		Date nu = new Date(record.getMillis());
        sb.append(df.format(nu)).append(": ").append(record.getLevel().getLocalizedName()).append(": ").append(formatMessage(record)).append(LINE_SEPARATOR);
        return sb.toString();
	}
}

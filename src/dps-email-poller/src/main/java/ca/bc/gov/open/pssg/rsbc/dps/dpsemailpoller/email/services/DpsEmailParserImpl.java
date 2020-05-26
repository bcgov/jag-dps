package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailContent;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Dps email parser has function to extract information from email body
 */
public class DpsEmailParserImpl implements DpsEmailParser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String MONTH_KEY = "MONTHNUM";
    private static final String MONTH_DAY_KEY = "MONTHDAY";
    private static final String YEAR_KEY = "YEAR";

    private static final String HOUR_KEY = "HOUR";
    private static final String MINUTE_KEY = "MINUTE";
    private static final String SECOND_KEY = "SECOND";

    private static final String TIME_KEY = "TIME";
    private static final String AA_KEY = "AA";
    private static final String JOB_ID_KEY = "JOB_ID";
    private static final String PAGE_COUNT_KEY = "PAGECOUNT";
    private static String PHONE_NUMBER_KEY = "PHONENUMBER";
    private static String GROK_PATTERN = ".*\\sfrom\\s(?<" + PHONE_NUMBER_KEY + ">.*)\\son\\s" +
            "%{" + MONTH_KEY + "}/%{" + MONTH_DAY_KEY + "}/%{" + YEAR_KEY + "}\\sat\\s" +
            "%{TIME:" + TIME_KEY + "}\\s" +
            "(?<" + AA_KEY + ">AM|am|PM|pm)\\s.*of\\s" +
            "%{NUMBER:" + PAGE_COUNT_KEY + "}\\s.*JobID:\\s" +
            "%{WORD:" + JOB_ID_KEY + "}";


    /**
     * Parse the content of the body and extract DpsEmailContent
     * @param html
     * @return
     */
    public DpsEmailContent parseEmail(String html) {

        Map<String, Object> emailMap = parseString(getEmailBodyFromHtml(html));

        if(!ValidateMap(emailMap)) {
            // TODO: currently the validation is ignored and just return an empty object, business should reevaluate this rule.
            logger.warn("email does not content valid body.");
            return new DpsEmailContent.Builder().build();
        }

        return new DpsEmailContent
                .Builder()
                .withPhoneNumber(emailMap.get(PHONE_NUMBER_KEY).toString())
                .withJobId(emailMap.get(JOB_ID_KEY).toString())
                .withPageCount(Integer.parseInt(emailMap.get(PAGE_COUNT_KEY).toString()))
                .withDate(buildDate(emailMap))
                .build();
    }

    private static String getEmailBodyFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("body");
        return elements.text();
    }

    private static Date buildDate(Map<String, Object> emailMap) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.YEAR, Integer.parseInt(emailMap.get(YEAR_KEY).toString()));
        cal.set(Calendar.MONTH, Integer.parseInt(emailMap.get(MONTH_KEY).toString()) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(emailMap.get(MONTH_DAY_KEY).toString()));
        cal.set(Calendar.AM_PM, emailMap.get(AA_KEY).toString().equalsIgnoreCase("am") ? Calendar.AM : Calendar.PM);
        cal.set(Calendar.HOUR, Integer.parseInt(emailMap.get(HOUR_KEY).toString()));
        cal.set(Calendar.MINUTE, Integer.parseInt(emailMap.get(MINUTE_KEY).toString()));
        cal.set(Calendar.SECOND, Integer.parseInt(emailMap.get(SECOND_KEY).toString()));
        return cal.getTime();
    }

    private static Map<String, Object> parseString(String body) {

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        final Grok grok = grokCompiler.compile(GROK_PATTERN);
        Match gm = grok.match(body);
        return gm.capture();

    }

    private boolean ValidateMap(Map<String, Object> emailMap) {

        String[] keys = {MONTH_KEY, MONTH_DAY_KEY, YEAR_KEY, HOUR_KEY, MINUTE_KEY, SECOND_KEY, TIME_KEY, AA_KEY,
                JOB_ID_KEY, PAGE_COUNT_KEY, PHONE_NUMBER_KEY};

        for (int i = 0; i < keys.length; i++) {
            if(!emailMap.containsKey(keys[i]))
                return false;
        }

        return true;

    }

}

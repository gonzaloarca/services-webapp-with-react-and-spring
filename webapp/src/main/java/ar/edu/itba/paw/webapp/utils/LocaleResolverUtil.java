package ar.edu.itba.paw.webapp.utils;

import java.util.List;
import java.util.Locale;

public class LocaleResolverUtil {
    public static Locale resolveLocale(List<Locale> languageTags){
        for (Locale languageTag : languageTags) {
            if(languageTag.toLanguageTag().contains("en"))
                return Locale.forLanguageTag("en");
            if(languageTag.toLanguageTag().contains("es"))
                return Locale.forLanguageTag("es");
        }
        //if we dont support the language we return null
        return Locale.getDefault();
    }
}

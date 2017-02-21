package me.efraimgentil.mymusic.util;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by efraimgentil on 21/02/17.
 */
@Service
public class NormalizerUtil {

    public String normalize(String dirName) {
        String nfdNormalizedString = Normalizer.normalize(dirName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("\\s", "")
                .toLowerCase();
    }

}

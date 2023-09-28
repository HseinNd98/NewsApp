package hussein.nasereddine.areeba_challenge.models.data_models.date_time;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import hussein.nasereddine.areeba_challenge.enums.ArticleLanguage;

public class DateTimeModel {
    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Nullable
    private final LocalDateTime localDateTime;

    public DateTimeModel(@Nullable String dateTimeString) {
        try {
            if(dateTimeString != null && !dateTimeString.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));
                this.localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            }else{
                this.localDateTime = null;
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format. Expected format: '"+DATE_TIME_FORMAT+"'. Input data: "+dateTimeString, e);
        }
    }

    @NonNull
    public String getTimeAgo(@NonNull ArticleLanguage language) {
        if (localDateTime == null) {
            return "";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if(days > 0){
            return getReadableDate(language);
        }else if(hours > 0){
            if(language == ArticleLanguage.ENGLISH){
                return hours + (hours < 2 ? " hour" : " hours");
            }else{
                return hours + " ساعة";
            }
        }else if(minutes > 0){
            if(language == ArticleLanguage.ENGLISH){
                return minutes + (minutes < 2 ? " minute" : " minutes");
            }else{
                return minutes + " دقيقة";
            }
        }else {
            if(language == ArticleLanguage.ENGLISH){
                return "just now";
            }else{
                return "الآن";
            }
        }
    }

    @NonNull
    public String getReadableDate(@NonNull ArticleLanguage language){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd");
        if(language == ArticleLanguage.ARABIC){
            formatter = formatter.withLocale(Locale.forLanguageTag("ar"));
        }

        return localDateTime == null ? "" : localDateTime.format(formatter);
    }
}

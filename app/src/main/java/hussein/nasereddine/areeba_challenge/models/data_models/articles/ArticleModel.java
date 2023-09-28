package hussein.nasereddine.areeba_challenge.models.data_models.articles;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import hussein.nasereddine.areeba_challenge.models.data_models.date_time.DateTimeModel;
import hussein.nasereddine.areeba_challenge.utils.data.JsonDataParser;

public class ArticleModel extends JsonDataParser {
    private final String id, title, body, imageUrl, language, date, time;
    private final DateTimeModel dateTime;

    public ArticleModel(@NonNull JSONObject jsonObject){
        this.id = tryToGet(jsonObject, "uri");
        this.title = tryToGet(jsonObject, "title");
        this.body = tryToGet(jsonObject, "body");
        this.imageUrl = tryToGet(jsonObject, "image");
        this.language = tryToGet(jsonObject, "language");
        this.date = tryToGet(jsonObject, "date");
        this.time = tryToGet(jsonObject, "time");
        this.dateTime = new DateTimeModel(String.format("%s %s", date, time));
    }

    public DateTimeModel getDateTime() {
        return dateTime;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLanguage() {
        return language;
    }
}

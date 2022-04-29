import fun.listenia.mongolib.Manager;
import org.bson.Document;

import java.util.List;

public class Economy extends Manager {

    public Economy () {
        super("villages", "economy");
    }


    public void addScore (String village, int score) {

        // a faire:
        // skip, limit, sort, projection

        this.query((query) -> {
            query.equals("village", village);
            query.greaterThanOrEquals("members", score);
        }).values();

        this.update((query, update) -> {
            query.equals("village", village);
            update.inc("score", score);
        }).execute();

    }





}

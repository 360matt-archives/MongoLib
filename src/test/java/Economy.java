import fun.listenia.mongolib.Manager;
import fun.listenia.mongolib.utils.Criteria;
import fun.listenia.mongolib.utils.Sort;
import fun.listenia.mongolib.utils.Update;
import org.bson.Document;

import java.util.List;

public class Economy extends Manager {

    public Economy () {
        super("villages", "economy");
    }

    public boolean exist (String village) {
        return this.finder(
                Criteria.EQUALS("village", village)
        ).exists();
    }

    public List<Document> getBestsWithSize (int size, int nb) {
        return this.finder(
                Criteria.GREATER_THAN_OR_EQUALS_LIST("members", size),
                Sort.DESC("score")
        ).limit(nb).values();
    }

    public void addScore (String village, int score) {
        this.finder(Criteria.EQUALS("village", village)
        ).update(update -> {
            update.increment("score", score);
        });
    }





}

import fun.listenia.mongolib.Manager;
import fun.listenia.mongolib.MongoLib;
import fun.listenia.mongolib.assistants.Query;
import fun.listenia.mongolib.converters.Element;
import fun.listenia.mongolib.converters.Index;
import fun.listenia.mongolib.converters.Required;

import java.util.List;

public class Economy extends Manager<Economy.VillageEconomy> {

    public static class VillageEconomy extends Element {
        @Required
        @Index (unique = true)
        public String village;
        public int members;
        public int score;
    }

    public Economy () {
        super("villages", "economy", VillageEconomy.class);
    }

    public boolean existVillage (String village) {
        return this.exists(Query.equals("village", village));
    }

    public void addVillage (VillageEconomy villageEconomy) {
        this.insert(villageEconomy);
    }

    public VillageEconomy getVillage (String village) {
        return this.query((query -> {
            query.equals("village", village);
        })).element();
    }

    public void updateVillage (VillageEconomy villageEconomy) {
        this.update(((query, update) -> {
            query.equals("village", villageEconomy.village);
            update.set("members", villageEconomy.members);
            update.set("score", villageEconomy.score);
        }));
    }

    public List<VillageEconomy> topScore (int limit) {
        return this.query((query -> {
            query.sort().desc("score");
            query.limit(limit);
        })).elements();
    }





}

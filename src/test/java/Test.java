import fun.listenia.mongolib.MongoLib;
import fun.listenia.mongolib.assistants.Query;

public class Test {

    public static void main(String[] args) {

        MongoLib.DEFAULT.connect("mongodb+srv://root:CeciEstUnTest@cluster0.dg6zd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");



        Economy economy = new Economy();
        economy.deleteAll();

        Query<?> q = economy.query(((query) -> {
            query.sort().near("field", 100, 300, 500);
        }));

        // ** ** **

        Economy.VillageEconomy villageEconomy = new Economy.VillageEconomy();
        villageEconomy.village = "Han ouais";
        villageEconomy.score = 400;
        villageEconomy.members = 10;
        economy.insert(villageEconomy);

        villageEconomy.village = "Ben voyons";
        villageEconomy.update();

        // ** ** **

        economy.insert((x) -> {
            x.village = "Han ouais";
            x.score = 400;
            x.members = 10;
        });

        // ** ** **

        economy.topScore(10).forEach((x) -> {
            System.out.println(x.village + " " + x.score);
        });

    }

}

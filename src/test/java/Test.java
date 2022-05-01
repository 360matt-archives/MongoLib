import fun.listenia.mongolib.MongoAuth;
import fun.listenia.mongolib.MongoLib;

public class Test {

    public static void main(String[] args) {

        MongoLib.init("mongodb+srv://root:CeciEstUnTest@cluster0.dg6zd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");



        Economy economy = new Economy();

        economy.topScore(10).forEach((x) -> {
            System.out.println(x.village + " " + x.score);
        });

    }

}

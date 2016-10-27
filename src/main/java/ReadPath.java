import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by mikhaill on 5/6/2016.
 */
public class ReadPath {

    public static Properties getUIMapping()
    {
        Properties defaultPath = new Properties();
        try {
            FileInputStream in = new FileInputStream("src/main/resources/UI.properties");
            defaultPath.load(in);
            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return defaultPath;
    }

}

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by mikhaill on 5/6/2016.
 */
public class ReadProperties {

        public static Properties getProperties()
        {
            Properties defaultProps = new Properties();
            try {
                FileInputStream in = new FileInputStream("src/main/resources/config.properties");
                defaultProps.load(in);
                in.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }


            return defaultProps;
        }


}

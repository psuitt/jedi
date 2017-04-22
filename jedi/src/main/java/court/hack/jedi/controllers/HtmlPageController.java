package court.hack.jedi.controllers;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by b888pcs on 4/22/2017.
 */
@ApplicationScoped
public class HtmlPageController {

    @Inject
    private ServletContext servletContext;

    public String getHtmlPage(final String page) {

        final InputStream resourceAsStream = servletContext.getResourceAsStream("resources/" + page);

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(resourceAsStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            // TODO
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO
                }
            }
        }

        return sb.toString();
    }

}

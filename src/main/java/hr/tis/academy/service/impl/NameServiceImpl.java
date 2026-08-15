package hr.tis.academy.service.impl;

import hr.tis.academy.service.NameService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NameServiceImpl implements NameService {
    public String printMessages(List<String> names) {
        List<String> finalNames = new ArrayList<>();
        if (names != null) {
            List<String> messages = new ArrayList<>();
            messages.add(" - Hello!");
            messages.add(" - Hi!");
            messages.add(" - Greetings!");


            String output = "<html> <p>";

            names.stream().forEach(n -> {
                finalNames.add(n += messages.get((int) (Math.random() * 3)));
            });

            output = output.concat(String.join("\n", finalNames));
            output += "</p> </html>";
            return output;
        } else {

            return "<html> <p>Niste unijeili niti jedno ime</p> <html>";
        }

    }
}

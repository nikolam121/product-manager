package hr.tis.academy.controller;

import hr.tis.academy.common.dto.*;

import hr.tis.academy.service.HelloService;
import hr.tis.academy.service.ImageService;
import hr.tis.academy.service.NameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Hello", description = "Hello management")
@RequestMapping
public class HelloController {
    private final HelloService helloService;
    private final NameService nameService;
    private final ImageService imageService;

    public HelloController(HelloService helloService, NameService nameService, ImageService imageService) {
        this.helloService = helloService;
        this.nameService = nameService;
        this.imageService = imageService;
    }


    @GetMapping("/common/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("HELLO", HttpStatus.OK);
    }

    @GetMapping("/common/hello-json")
    public ResponseEntity<HelloResponse> helloJson(@RequestParam(name = "helloString") String param) {
        return new ResponseEntity<>(new HelloResponse(param), HttpStatus.OK);
    }

    @GetMapping("common/days-of-week")
    public ResponseEntity<DayOfWeekResponse> dayOfWeek() {
        return new ResponseEntity<>(helloService.daysOfWeek(), HttpStatus.OK);
    }

    @GetMapping("common/names")
    public ResponseEntity<String> name(@RequestParam(name = "name", required = false) List<String> names) {
        return new ResponseEntity<>(nameService.printMessages(names), HttpStatus.OK);
    }

    @GetMapping(path = "common/image", produces = "image/png")
    public ResponseEntity<byte[]> image(@RequestParam(name = "text", defaultValue = "Hello TIS!") String text,
                                        @RequestParam(name = "width") int width,
                                        @RequestParam(name = "height") int height,
                                        @RequestParam(name = "red") int red,
                                        @RequestParam(name = "green") int green,
                                        @RequestParam(name = "blue") int blue) {
        return imageService.buildImageResponseEntity(text, width, height, red, green, blue);
    }

    @GetMapping("common/is-weekend")
    public ResponseEntity<WeekendResponse> isWeekend(@RequestParam(name = "day") String day) {
        return new ResponseEntity<>(new WeekendResponse(helloService.isWeekend(day)), HttpStatus.OK);
    }





}

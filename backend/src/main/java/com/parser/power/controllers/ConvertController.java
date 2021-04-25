package com.parser.power.controllers;

import com.parser.power.models.ConvertType;
import com.parser.power.services.ConverterRouter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ConvertController {

    @PostMapping("/convert")
    public String makeConvertTask(@RequestBody String xml,
                                   @RequestParam("current") ConvertType current,
                                   @RequestParam("target") ConvertType target)
            throws Exception {
        ConverterRouter converterRouter = new ConverterRouter(xml, current);
        return converterRouter.convertTo(target);
    }
}

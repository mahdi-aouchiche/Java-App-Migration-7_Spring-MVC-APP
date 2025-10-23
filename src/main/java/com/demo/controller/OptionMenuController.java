package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class OptionMenuController {

	@RequestMapping(path = "/optionMenu", method = RequestMethod.GET)
    public String showOptionMenu() {
            return "option-menu";
    }
}
